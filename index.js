import {
    Platform,
    NativeModules,
    NativeAppEventEmitter
} from 'react-native';
const ios = Platform.OS === 'ios';
const android = Platform.OS === 'android';
const Dialog = NativeModules.DialogModule;

const options_Alert = {
  title: "Title",
  titleTextColor: [0, 0, 0, 1],
  content: "This is content!",
  contentTextColor: [66, 66, 66, 1],
  button: "确认",
  buttonTextColor: [0, 0, 0, 1],
  callback: () => {}
}

const options_Confirm = {
  title: "Title",
  titleTextColor: [0, 0, 0, 1],
  content: "This is content!",
  contentTextColor: [66, 66, 66, 1],
  button: ["取消", "确认"],
  buttonTextColor: [[0, 0, 0, 1], [255, 0, 0, 1]],
  callback: () => {}
}

export default {
  alert(params){
    const opt = {
      ...options_Alert,
      ...params
    };
    Dialog._alert(opt)
    //there are no `removeListener` for NativeAppEventEmitter & DeviceEventEmitter
    this.listener && this.listener.remove()
    this.listener = NativeAppEventEmitter.addListener('dialogEvent', event => {
      opt.callback(event['type'])
    })
  },

  confirm(params){
    const opt = {
      ...options_Confirm,
      ...params
    };
    Dialog._confirm(opt)
    //there are no `removeListener` for NativeAppEventEmitter & DeviceEventEmitter
    this.listener && this.listener.remove()
    this.listener = NativeAppEventEmitter.addListener('dialogEvent', event => {
      opt.callback(event['type'])
    })
  },

  hide(){
    Dialog.hide()
  },
  
  isDialogAlertShow(fn){
    Dialog.isPickerShow((err, status) => {
      let returnValue = null
      if(android){
        returnValue = err ? false : status
      }
      else if(ios){
        returnValue = !err
      }
      fn && fn(returnValue)
    })
  }
}