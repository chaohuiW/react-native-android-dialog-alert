# react-native-android-dialog-alert

### Documentation

#### Params

|Key | Type | Default| Support | Description |
| --- | --- | ---- | ------ | ----------- |
|title                 | string  | Title                           |     Android  |   |
|titleTextColor        | array   | [0, 0, 0, 1]                    |     Android  |   |
|content               | string  | This is content!                |     Android  |   |
|contentTextColor      | array   | [66, 66, 66, 1]                 |     Android  |   |
|button                | string  | '取消'                           |     Android(alert方式)  |   |
|button                | array   | ['取消', '确认']                  |     Android(confirm方式)  |   |
|buttonTextColor       | array   | [0, 0, 0, 1]                     |     Android(alert方式)  |   |
|buttonTextColor       | array   | [[0, 0, 0, 1], [255, 0, 0, 1]]   |     Android(confirm方式)  |   |
|callback              | function|                                 |     Android  |   |

#### Methods

|Key | Support | Description |
| --- | ---- | ----------- |
|alert             | Android | alert and pass parameters to dialog     |
|confirm           | Android | confirm and pass parameters to dialog   |
|hide              | Android | hide dialog                             |
|isDialogAlertShow | Android | get status of dialog, return a boolean  |


### Usage

#### Step 1 - install

```javascript
	npm install react-native-android-dialog-alert --save
```

#### Step 2 - link

```javascript
	react-native link
```

#### Step 3 - import and use in project

```javascript
import Dialog from 'react-native-android-dialog-alert';

Dialog.alert({
  content: "this is content!",
  button: "确定",
  callback: res => {
    console.log(res)
  }
});

Dialog.confirm({
  content: "this is content!",
  button: ["取消", "确定"],
  callback: res => {
    console.log(res)
  }
});
```