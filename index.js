import {
    Platform,
    NativeModules,
    NativeAppEventEmitter
} from 'react-native';
const ios = Platform.OS === 'ios';
const android = Platform.OS === 'android';
const Dialog = NativeModules.DialogModule;
const options = {
  title: "Title",
  titleTextColor: [0, 0, 0, 1],
  content: "This is content!",
  contentTextColor: [66, 66, 66, 1],
  button: ["取消", "确认"],
  buttonTextColor: [[0, 0, 0, 1], [255, 0, 0, 1]],
  callback: () => {}
}
export default {
  init(params){
    const opt = {
      ...options,
      ...params
    };
    Dialog._init(opt)
    //there are no `removeListener` for NativeAppEventEmitter & DeviceEventEmitter
    this.listener && this.listener.remove()
    this.listener = NativeAppEventEmitter.addListener('dialogEvent', event => {
      opt.callback(event['type'])
    })
  },
  
  show(){
    Dialog.show()
  },

  hide(){
    Dialog.hide()
  },

  toggle(){
    this.isPickerShow(show => {
      if(show){
        this.hide()
      }
      else{
        this.show()
      }
    })
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