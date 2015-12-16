package com.xiwang.jxw.event;

import java.util.ArrayList;

/**
 * Created by sunshine on 15/12/16.
 */
public class PickImageEvent {

   public String fromTag;
   public ArrayList<String> picklist;
    public PickImageEvent(String fromTag,ArrayList<String>  picklist){
        this.fromTag=fromTag;
        this.picklist=picklist;
    }

}
