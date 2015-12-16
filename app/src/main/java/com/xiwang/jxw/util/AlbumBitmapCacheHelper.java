package com.xiwang.jxw.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.WindowManager;

import com.xiwang.jxw.config.TApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: zzp
 * @since: 2015-06-10
 * Description: ���ҳ����ͼƬ��ʡ�ڴ棬��ֹoom
 */
public class AlbumBitmapCacheHelper {
    //�̰߳�ȫ�ĵ���ģʽ
    private volatile static AlbumBitmapCacheHelper instance = null;
    private LruCache<String, Bitmap> cache;
    /**
     * �����Ż�ͼƬ��չʾЧ�������浱ǰ��ʾ��ͼƬpath
     */
    private ArrayList<String> currentShowString;
//    private ContentResolver cr;

    private AlbumBitmapCacheHelper() {
        //����1/4������ʱ�ڴ��ͼƬ��ʾ
        final int memory = (int) (Runtime.getRuntime().maxMemory() / 1024 / 4);

        cache = new LruCache<String, Bitmap>(memory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //��ȡÿ��bitmap��С
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        currentShowString = new ArrayList<String>();
//        cr = AppContext.getInstance().getContentResolver();
    }

    /**
     * �ͷ����е��ڴ�
     */
    public void releaseAllSizeCache(){
        cache.evictAll();
        cache.resize(1);
    }

    public void releaseHalfSizeCache() {
        cache.resize((int) (Runtime.getRuntime().maxMemory() / 1024 / 8));
    }

    public void resizeCache() {
        cache.resize((int) (Runtime.getRuntime().maxMemory() / 1024 / 4));
    }

    /**
     * ѡ����ϣ�ֱ���ͷŻ�����ռ���ڴ�
     */
    public void clearCache() {
        cache.evictAll();
        cache = null;
        instance = null;
    }

    public static AlbumBitmapCacheHelper getInstance() {
        if (instance == null) {
            synchronized (AlbumBitmapCacheHelper.class) {
                if (instance == null) {
                    instance = new AlbumBitmapCacheHelper();
                }
            }
        }
        return instance;
    }

    /**
     * ͨ��ͼƬ��path�ص���ͼƬ��bitmap
     *
     * @param path     ͼƬ��ַ
     * @param width    ��Ҫ��ʾͼƬ�Ŀ�ȣ�0������ʾ����ͼƬ
     * @param height   ��Ҫ��ʾͼƬ�ĸ߶ȣ�0������ʾ����ͼƬ
     * @param callback ����bitmap�ɹ��ص�
     * @param objects  ����ֱ�ӷ��ر�ʶ
     */
    public Bitmap getBitmap(final String path, int width, int height, final ILoadImageCallback callback, Object... objects){
        Bitmap bitmap = getBitmapFromCache(path, width, height);
        //����ܹ��ӻ����л�ȡ����Ҫ���ͼƬ����ֱ�ӻص�
        if (bitmap != null) {
            Log.e("zhao", "get bitmap from cache");
        } else {
            decodeBitmapFromPath(path, width, height, callback, objects);
        }
        return bitmap;
    }

    //try another size to get better display
    ThreadPoolExecutor tpe = new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
//    ExecutorService tpe = Executors.newFixedThreadPool(1);

    /**
     * ͨ��path��ȡͼƬbitmap
     */
    private void decodeBitmapFromPath(final String path, final int width, final int height, final ILoadImageCallback callback, final Object... objects) throws OutOfMemoryError {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                callback.onLoadImageCallBack((Bitmap) msg.obj, path, objects);
            }
        };
        //��ֹ���߳̿���
        tpe.execute(new Runnable() {
            @Override
            public void run() {
                if (!currentShowString.contains(path)||cache==null) {
                    return;
                }
                Bitmap bitmap = null;
                //���ش�ͼ,��Ļ���Ϊ׼
                if (width == 0 || height == 0) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(path, options);
                    options.inSampleSize = computeScale(options, ((WindowManager) (TApplication.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth(), ((WindowManager) (TApplication.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getWidth());
                    options.inJustDecodeBounds = false;
                    try {
                        bitmap = BitmapFactory.decodeFile(path, options);
                    } catch (OutOfMemoryError e) {
                        releaseAllSizeCache();
                        bitmap = BitmapFactory.decodeFile(path, options);
                    }
                } else {
                    //����Сͼ����һ������tempĿ¼��ȡ��ͼƬָ����С�Ļ��棬���ȡ������
                    // �ڶ���������samplesize,���samplesize > 4,
                    // ��������ѹ�����ͼƬ����tempĿ¼�£��Ա��´ο���ȡ��
                    String hash = CommonUtil.md5(path+"_"+width+"_"+height);
                    File file = new File(CommonUtil.getDataPath());
                    if (!file.exists())
                        file.mkdirs();
                    //��ʱ�ļ����ļ���
                    String tempPath = CommonUtil.getDataPath() + hash + ".temp";
                    File picFile = new File(path);
                    File tempFile = new File(tempPath);
                    //������ļ�����,����temp�ļ��Ĵ���ʱ��Ҫԭ�ļ�֮��
                    if (tempFile.exists() && (picFile.lastModified() <= tempFile.lastModified()))
                        bitmap = BitmapFactory.decodeFile(tempPath);
                    //�޷�����ʱ�ļ�������ͼĿ¼�ҵ���ͼƬ������ִ�еڶ���
                    if (bitmap == null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(path, options);
                        options.inSampleSize = computeScale(options, width, height);
                        options.inJustDecodeBounds = false;
                        //��ȡ�ֻ��Դ�����ͼ,�ٶ����ɺ��������Ը÷�������
//                    if(objects.length != 0){
//                        long start = System.currentTimeMillis();
//                        bitmap = MediaStore.Images.Thumbnails.getThumbnail(cr, Long.parseLong(objects[0].toString()),
//                                MediaStore.Video.Thumbnails.MINI_KIND, options);
//                    }else{
                        try {
                            bitmap = BitmapFactory.decodeFile(path, options);
                        }catch (OutOfMemoryError error){
                            bitmap = null;
                        }
                        if (bitmap != null && cache!=null) {
                            bitmap = centerSquareScaleBitmap(bitmap, ((bitmap.getWidth() > bitmap.getHeight()) ? bitmap.getHeight() : bitmap.getWidth()));
                        }
                        //������,������ű�������4����ͼ�ļ��ػ�ǳ��������Խ���ͼ���浽��ʱĿ¼���Ա��´εĿ��ټ���
                        if (options.inSampleSize>=4 && bitmap!=null) {
                            try {
                                file = new File(tempPath);
                                if (!file.exists())
                                    file.createNewFile();
                                else {
                                    file.delete();
                                    file.createNewFile();
                                }
                                FileOutputStream fos = new FileOutputStream(file);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                                fos.write(baos.toByteArray());
                                fos.flush();
                                fos.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
//                    }
                    }else{
                        //��tempĿ¼���س�����ͼƬҲҪ���뵽cache��
                        if (bitmap != null && cache!=null) {
                            bitmap = centerSquareScaleBitmap(bitmap, ((bitmap.getWidth() > bitmap.getHeight()) ? bitmap.getHeight() : bitmap.getWidth()));
                        }
                    }
                }
                if (bitmap != null && cache!=null)
                    cache.put(path +"_"+ width +"_"+height, bitmap);
                Message msg = Message.obtain();
                msg.obj = bitmap;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * @param bitmap     ԭͼ
     * @param edgeLength ϣ���õ��������β��ֵı߳�
     * @return ���Ž�ȡ���в��ֺ��λͼ��
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }
        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        //��ͼ�н�ȡ���м�������β��֡�
        int xTopLeft = (widthOrg - edgeLength) / 2;
        int yTopLeft = (heightOrg - edgeLength) / 2;

        if (xTopLeft == 0 && yTopLeft == 0) return result;

        try {
            result = Bitmap.createBitmap(bitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
            bitmap.recycle();
        } catch (OutOfMemoryError e) {
            return result;
        }

        return result;
    }

    /**
     * �������ű���
     */
    private int computeScale(BitmapFactory.Options options, int width, int height) {
        if (options == null) return 1;
        int widthScale = (int)((float) options.outWidth / (float) width);
        int heightScale = (int)((float) options.outHeight / (float) height);
        //ѡ�����ű����ϴ���Ǹ�
        int scale = (widthScale > heightScale ? widthScale : heightScale);
        if (scale < 1) scale = 1;
        return scale;
    }

    /**
     * ��ȡlrucache�е�ͼƬ�������ͼƬ�Ŀ�Ⱥͳ����޷�����Ҫ��������򷵻�null
     *
     * @param path   ͼƬ��ַ,key
     * @param width  ��Ҫ��ͼƬ���
     * @param height ��Ҫ��ͼƬ����
     * @return ͼƬvalue
     */
    private Bitmap getBitmapFromCache(final String path, int width, int height) {
        return cache.get(path +"_"+ width +"_"+height);
    }

    /**
     * ��Ҫչʾ��path���뵽list
     */
    public void addPathToShowlist(String path) {
        currentShowString.add(path);
    }

    /**
     * ��չʾlist��ɾ����path
     */
    public void removePathFromShowlist(String path) {
        currentShowString.remove(path);
    }

    /**
     * ����ͼƬ�ɹ��Ľӿڻص�
     */
    public interface ILoadImageCallback {
        void onLoadImageCallBack(Bitmap bitmap, String path, Object... objects);
    }

    /**
     * �Ƴ���threads�е������߳�
     */
    public void removeAllThreads() {
        currentShowString.clear();
        for (Runnable runnable : tpe.getQueue()) {
            tpe.remove(runnable);
        }
    }

}
