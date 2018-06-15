# react-native-android-dialog-alert

### Documentation

#### Params

|Key | Type | Default| Support | Description |
| --- | --- | ---- | ------ | ----------- |
|title                 | string  | Title                           |     Android  |   |
|titleTextColor        | array   | [0, 0, 0, 1]                    |     Android  |   |
|content               | string  | This is content!                |     Android  |   |
|contentTextColor      | array   | [66, 66, 66, 1]                 |     Android  |   |
|button                | array   | ['取消', '确认']                  |     Android  |   |
|buttonTextColor       | array   | [[0, 0, 0, 1], [255, 0, 0, 1]]  |     Android  |   |
|callback              | function|                                 |     Android  |   |

#### Methods

|Key | Support | Description |
| --- | ---- | ----------- |
|alert             | Android | alert and pass parameters to dialog     |
|hide              | Android | hide dialog                             |
|isDialogAlertShow | Android | get status of dialog, return a boolean  |


### Usage

#### Step 1 - install

```javascript
	npm install react-native-android-dialog-alert --save
```

#### Step 2 - link

```
	react-native link
```

#### Step 3 - import and use in project

```javascript
import DialogAlert from 'react-native-android-dialog-alert';

DialogAlert.alert({
  callback: res => {
    console.log(res);
  }
});
	
```