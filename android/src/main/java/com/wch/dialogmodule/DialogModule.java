package com.wch.dialogmodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.w3c.dom.Text;

import static android.graphics.Color.argb;

/**
 *
 * Created by wangchaohui on 2018/6/12.
 *
 */

public class DialogModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

  private Dialog dialog = null;

  private static final String MODULE_NAME = "DialogModule";
  private static final String DIALOG_TITLE = "title";
  private static final String DIALOG_TITLE_TEXT_COLOR = "titleTextColor";
  private static final String DIALOG_CONTENT = "content";
  private static final String DIALOG_CONTENT_TEXT_COLOR = "contentTextColor";
  private static final String DIALOG_BUTTON = "button";
  private static final String DIALOG_BUTTON_TEXT_COLOR = "buttonTextColor";

  private static final String DIALOG_EVENT_NAME = "dialogEvent";
  private static final String EVENT_KEY_LEFT = "0";
  private static final String EVENT_KEY_RIGHT = "1";

  public DialogModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return MODULE_NAME;
  }

  // 一个带ReactMethod的方法并传入ReadableMap ReadableArray
//  @ReactMethod
//  public void showDialog(ReadableMap object, ReadableArray btnsArray, final Callback callback) {
//
//    Activity activity = getCurrentActivity();
//    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//
//    builder.setTitle(object.getString("title"))
//      .setMessage(object.getString("message"));
//
//    if (btnsArray.size() > 1) {
//      final String btnItem_1 = btnsArray.getString(0);
//      builder.setNegativeButton(btnItem_1, new DialogInterface.OnClickListener(){
//        public void onClick(DialogInterface dialog, int id) {
//          dialog.dismiss();
//          callback.invoke(btnItem_1);
//        }
//      });
//    } else {
//      builder.setPositiveButton("取消", new DialogInterface.OnClickListener(){
//        public void onClick(DialogInterface dialog, int id) {
//          dialog.dismiss();
//          callback.invoke();
//        }
//      });
//    }
//    if (btnsArray.size() == 2) {
//      final String btnItem_2 = btnsArray.getString(1);
//      builder.setPositiveButton(btnItem_2, new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int id) {
//          dialog.dismiss();
//          callback.invoke(btnItem_2);
//        }
//      });
//    }
//
//    builder.create().show();
//  }

  @ReactMethod
  public void _alert(final ReadableMap options) {
    Activity activity = getCurrentActivity();
    if (activity != null){
      View view = activity.getLayoutInflater().inflate(R.layout.dialog_view, null);
      TextView dialogTitle = view.findViewById(R.id.dialog_title);
      TextView dialogContent = view.findViewById(R.id.dialog_content);
      TextView dialogLeftBtn = view.findViewById(R.id.dialog_left_btn);
      View dialogMiddleLine = view.findViewById(R.id.dialog_middle_line);
      TextView dialogRightBtn = view.findViewById(R.id.dialog_right_btn);

      // 设置Title
      if (options.hasKey(DIALOG_TITLE)) {
        String optionsTitle = options.getString(DIALOG_TITLE);
        if (optionsTitle.isEmpty()) {
          dialogTitle.setVisibility(View.GONE);
        } else {
          dialogTitle.setVisibility(View.VISIBLE);
          dialogTitle.setText(optionsTitle);
        }
      } else {
        dialogTitle.setVisibility(View.VISIBLE);
        dialogTitle.setText("提示");
      }

      // 设置Title字体样式
      if (options.hasKey(DIALOG_TITLE_TEXT_COLOR)) {
        ReadableArray array = options.getArray(DIALOG_TITLE_TEXT_COLOR);
        int[] colors = getColor(array);
        dialogTitle.setTextColor(argb(colors[3], colors[0], colors[1], colors[2]));
      }

      String optionsContent = options.getString(DIALOG_CONTENT);
      dialogContent.setText(optionsContent);

      // 设置Content字体样式
      if (options.hasKey(DIALOG_CONTENT_TEXT_COLOR)) {
        ReadableArray array = options.getArray(DIALOG_CONTENT_TEXT_COLOR);
        int[] colors = getColor(array);
        dialogContent.setTextColor(argb(colors[3], colors[0], colors[1], colors[2]));
      }


      if (options.hasKey(DIALOG_BUTTON)) {
        ReadableArray button = options.getArray(DIALOG_BUTTON);
        if (button.size() == 1) {
          dialogLeftBtn.setText(button.getString(0));
          dialogMiddleLine.setVisibility(View.GONE);
          dialogRightBtn.setVisibility(View.GONE);
        } else if (button.size() == 2) {
          dialogLeftBtn.setText(button.getString(0));
          dialogRightBtn.setText(button.getString(1));
        }
      } else {
        dialogLeftBtn.setText("确定");
        dialogMiddleLine.setVisibility(View.GONE);
        dialogRightBtn.setVisibility(View.GONE);
      }

      if (options.hasKey(DIALOG_BUTTON_TEXT_COLOR)) {
        ReadableArray buttonTextColor = options.getArray(DIALOG_BUTTON_TEXT_COLOR);
        if (buttonTextColor.size() == 1) {
          ReadableArray array = buttonTextColor.getArray(0);
          int[] colors = getColor(array);
          dialogLeftBtn.setTextColor(argb(colors[3], colors[0], colors[1], colors[2]));

        } else if (buttonTextColor.size() == 2) {
          ReadableArray array_L = buttonTextColor.getArray(0);
          int[] colors_L = getColor(array_L);
          dialogLeftBtn.setTextColor(argb(colors_L[3], colors_L[0], colors_L[1], colors_L[2]));

          ReadableArray array_R = buttonTextColor.getArray(1);
          int[] colors_R = getColor(array_R);
          dialogRightBtn.setTextColor(argb(colors_R[3], colors_R[0], colors_R[1], colors_R[2]));
        }
      }

      dialogLeftBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
          commonEvent(EVENT_KEY_LEFT);
          hide();
        }
      });
      dialogRightBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
          commonEvent(EVENT_KEY_RIGHT);
          hide();
        }
      });
      if (dialog == null){
        dialog = new Dialog(activity, R.style.dialog);
        dialog.setContentView(view);
      } else {
        dialog.dismiss();
        dialog.setContentView(view);
      }

      show();
    }
  }

  @ReactMethod
  public void show() {
    if (dialog == null) {
      return;
    }
    if (!dialog.isShowing()) {
      dialog.show();
    }
  }

  @ReactMethod
  public void hide() {
    if (dialog == null) {
      return;
    }
    if (dialog.isShowing()) {
      dialog.dismiss();
    }
  }

  @Override
  public void onHostResume() {

  }

  @Override
  public void onHostPause() {
    hide();
    dialog = null;
  }

  @Override
  public void onHostDestroy() {
    hide();
    dialog = null;
  }

  @ReactMethod
  private int[] getColor(ReadableArray array) {
    int[] colors = new int[4];
    for (int i = 0; i < array.size(); i++) {
      switch (i) {
        case 0:
        case 1:
        case 2:
          colors[i] = array.getInt(i);
          break;
        case 3:
          colors[i] = (int) (array.getDouble(i) * 255);
          break;
        default:
          break;
      }
    }
    return colors;
  }

  private void commonEvent(String eventKey) {
    WritableMap map = Arguments.createMap();
    map.putString("type", eventKey);
    sendEvent(getReactApplicationContext(), DIALOG_EVENT_NAME, map);
  }

  private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, params);
  }
}
