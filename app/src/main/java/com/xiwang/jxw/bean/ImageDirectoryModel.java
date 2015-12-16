package com.xiwang.jxw.bean;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author: zzp
 * @since: 2015-06-11
 * Description: һ��ͼƬ�ļ��ж�Ӧʵ��
 */
public class ImageDirectoryModel implements Serializable {


    /** ͼƬ��·��,��ӦͼƬ�Ƿ�ѡ�е����� */
    private ArrayList<SingleImageModel> images;

    public ImageDirectoryModel(){
        images = new ArrayList<SingleImageModel>();
    }

    public ArrayList<SingleImageModel> getImages() {
        return images;
    }

    /**
     * ��һ��ͼƬpath��ӽ�����ļ�����
     * @param path ͼƬ��ַ
     */
    public void addImage(String path, long date, long id){
        SingleImageModel image = new SingleImageModel(path, false, date, id);
        images.add(image);
    }

    public void addSingleImageModel(SingleImageModel model){
        images.add(model);
    }

    /**
     * ��һ��ͼƬ�Ӹ��ļ�����ɾ��
     * @param path ͼƬ��ַ
     */
    public void removeImage(String path){
        for (SingleImageModel image : images){
            if (image.isThisImage(path)){
                images.remove(image);
                break;
            }
        }
    }

    /**
     * ѡ�и�ͼƬ
     * @param path ͼƬ��ַ
     */
    public void setImage(String path){
        for (SingleImageModel image : images){
            if (image.isThisImage(path)){
                if(image.isPicked){
                    Log.e("zhao", "this image is picked!!!");
                }
                image.isPicked = true;
                break;
            }
        }
    }

    /**
     * ��ѡ�и�ͼƬ
     * @param path ͼƬ��ַ
     */
    public void unsetImage(String path){
        for (SingleImageModel image : images){
            if (image.isThisImage(path)){
                if(!image.isPicked){
                    Log.e("zhao", "this image isn't picked!!!");
                }
                image.isPicked = false;
                break;
            }
        }
    }

    /**
     * ת��ͼƬ��ѡ��״̬
     */
    public void toggleSetImage(int position){
        SingleImageModel model = images.get(position);
        model.isPicked = !model.isPicked;
    }

    /**
     * ת��ͼƬ��ѡ��״̬
     */
    public void toggleSetImage(String path){
        for (SingleImageModel model : images){
            if (model.path.equalsIgnoreCase(path)) {
                model.isPicked = !model.isPicked;
                break;
            }
        }
    }

    /**
     * ���ظ��ļ��е������ļ�����
     */
    public int getImageCounts(){
        return images.size();
    }

    /**
     * ����ͼƬ��λ�÷��ظ�ͼƬ��url
     * @param position ͼƬλ��
     */
    public String getImagePath(int position){
        return images.get(position).path;
    }

    /**
     * ����ͼƬ��λ�÷��ظ�ͼƬ�Ƿ�ѡ��
     * @param position ͼƬλ��
     */
    public boolean getImagePickOrNot(int position){
        return  images.get(position).isPicked;
    }

    /**
     * ���ļ������Ƿ���ѡ�е�ͼƬ
     */
    public boolean hasChoosePic(){
        for (SingleImageModel model : images){
            if (model.isPicked)
                return true;
        }
        return false;
    }
}
