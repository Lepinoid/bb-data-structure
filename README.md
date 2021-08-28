# bb-data-structure

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
  implementation("net.lepinoid:bb-data-structure-jvm:1.0")
}
```

### Multiplatform:

```kotlin
commonMain {
    dependencies {
        implementation("net.lepinoid:bb-data-structure:1.0")
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
