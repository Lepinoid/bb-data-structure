# bb-data-structure

![maven](https://img.shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Flepinoid.github.io%2Fmaven-repo%2Fnet%2Flepinoid%2Fbb-data-structure%2Fmaven-metadata.xml)

BlockBenchのプロジェクトファイル.bbmodelの情報をクラスに変換する用のライブラリ

[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) に対応しており，ファイルから直接読み込み，書き出しが可能

# 対応クラス

| ClassName     | Type          | Extension    |
| ------------- | ------------- | ------------ |
| BBModelData   | BBProjectFile | .bbmodeldata |
| ItemModelData | ItemModel     | .json        |



# Gradle

KotlinDSL:

```kotlin
repositories {
     maven { 
         name = "lepinoid"
         url = uri("https://lepinoid.github.io/maven-repo/")
     }
}

dependencies {
  implementation("net.lepinoid:bb-data-structure-jvm:5.0")
}
```

### Multiplatform:

```kotlin
commonMain {
    dependencies {
        implementation("net.lepinoid:bb-data-structure:5.0")
    }
}
```



# Examples

```kotlin
fun projectFile() {
    val bbModelData: String = //any project file string
    val decode = Json.decodeFromString<BBModelData>(BBModelData.serializer(), bbModelData)
    val encode = Json.encodeToString(BBModelData.serializer(), decode)
}
```
