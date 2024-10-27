#### Build and upload

Locally build a new apk and distribute it to Testapp.io with this command: 

```sh
./gradlew assembleDebug && ta-cli publish --config=ta-cli.json
```