# Easy Audio Recorder

> Two stylish design and
> Easy to use record functions

## Why this project exists

> In applications that include chat, it is often desired to record audio in m4a-mpeg format to be compatible with IOS. To avoid the confusion of algorithms on the chat screen you can use this library to add voice recording feature to your application with a few lines.

## Features and Usage

### BottomSheetFragment Usage

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

## Implementation

###### Add it in your root build.gradle at the end of repositories

```groovy
allprojects {
	repositories {
		...
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
