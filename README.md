# Easy Audio Recorder

> Two stylish design and
> Easy to use record functions

## Why this project exists

> In applications that include chat, it is often desired to record audio in **m4a-mpeg** format to be compatible with **IOS**. To avoid the confusion of algorithms on the chat screen you can use this library to add voice recording feature to your application with a few lines.

## Features and Usage


<img src="previews/1.jpeg" width="250"> <img src="previews/2.jpeg" width="250">  <img src="previews/3.jpeg" width="250">


### BottomSheetFragment Usage
[**here  :)**](https://github.com/AsynctaskCoffee/VoiceRecorder/blob/master/app/src/main/java/com/asynctaskcoffee/voicerecorder/BottomSheetUsageActivity.kt)
```kotlin
class BottomSheetUsageActivity : AppCompatActivity(), AudioRecordListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_usage)
    }
    
    fun openDialog(view: View) {
        VoiceSenderDialog(this).show(supportFragmentManager, "VOICE")
    }

    override fun onAudioReady(audioUri: String?) {
        TODO("Not yet implemented")
    }

    override fun onRecordFailed(errorMessage: String?) {
        TODO("Not yet implemented")
    }
}

```

### Normal Usage
[**here  :)**](https://github.com/AsynctaskCoffee/VoiceRecorder/blob/master/app/src/main/java/com/asynctaskcoffee/voicerecorder/WorkerUsageActivity.kt)
```kotlin
class WorkerUsageActivity : AppCompatActivity(), AudioRecordListener {

    lateinit var recorder: Recorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_usage)
    }

    fun initRecorder() {
        recorder = Recorder(this)
    }

    fun startRecord() {
        recorder.startRecord()
    }

    fun stopRecord() {
        recorder.stopRecording()
    }

    override fun onAudioReady(audioUri: String?) {
        TODO("Not yet implemented")
    }

    override fun onRecordFailed(errorMessage: String?) {
        TODO("Not yet implemented")
    }

}
```

### Extras

##### Change Record Path

```kotlin
fun setFileName(fileName: String?) {
   this.fileName = fileName
}
```

##### Easy MPlayer

```kotlin
lateinit var player: Player

override fun onAudioReady(audioUri: String?) {
    player = Player(this)
    player.injectMedia(audioUri)
}

fun playRecord(view: View) {
    if (player.player!!.isPlaying)
        player.stopPlaying()
    else player.startPlaying()
}
```

##### Customize Dialog Language with LangObj

```java
public LangObj() {
}

String record_audio_string = "Start Record";
String hold_for_record_string = "Hold for record";
String release_for_end_string = "Release for end record";
String listen_record_string = "You can listen record";
String stop_listen_record_string = "Stop Listen";
String stop_record_string = "Stop Record";
String send_record_string = "Send Record";

public LangObj(String record_audio_string, String hold_for_record_string, String release_for_end_string, String listen_record_string, String stop_listen_record_string, String stop_record_string, String send_record_string) {
    this.record_audio_string = record_audio_string;
    this.hold_for_record_string = hold_for_record_string;
    this.release_for_end_string = release_for_end_string;
    this.listen_record_string = listen_record_string;
    this.stop_listen_record_string = stop_listen_record_string;
    this.stop_record_string = stop_record_string;
    this.send_record_string = send_record_string;
}
```

```java
public VoiceSenderDialog(AudioRecordListener audioRecordListener, LangObj langObj) {
    this.langObj = langObj;
    this.audioRecordListener = audioRecordListener;
}
```

##### Customize Dialog Icons with LangObj

```java
public IconsObj() {
}

int ic_start_record = R.drawable.ic_start_record;
int ic_stop_play = R.drawable.ic_stop_play;
int ic_play_record = R.drawable.ic_play_record;
int ic_audio_delete = R.drawable.ic_audio_delete;
int ic_send_circle = R.drawable.ic_send_circle;
int ic_stop_record = R.drawable.ic_stop_record;

public IconsObj(int ic_start_record, int ic_stop_play, int ic_play_record, int ic_audio_delete, int ic_send_circle, int ic_stop_record) {
    this.ic_start_record = ic_start_record;
    this.ic_stop_play = ic_stop_play;
    this.ic_play_record = ic_play_record;
    this.ic_audio_delete = ic_audio_delete;
    this.ic_send_circle = ic_send_circle;
    this.ic_stop_record = ic_stop_record;
}
```

```java
public VoiceSenderDialog(AudioRecordListener audioRecordListener, IconsObj iconsObj) {
    this.iconsObj = iconsObj;
    this.audioRecordListener = audioRecordListener;
}
```


## Implementation

###### Add it in your root build.gradle at the end of repositories

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

###### Add the dependency

```groovy
dependencies {
    implementation 'com.github.AsynctaskCoffee:VoiceRecorder:0.1'
}
```

## License

```
Copyright 2020 Egemen ÖZOGUL

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
