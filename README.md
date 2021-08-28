# bb-data-structure

BlockBenchのプロジェクトファイル.bbmodelの情報をクラスに変換する用のライブラリ

[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) に対応しており，ファイルから直接読み込み，書き出しが可能

# 対応クラス

| ClassName     | Type          | Extension    |
| ------------- | ------------- | ------------ |
| BBModelData   | BBProjectFile | .bbmodeldata |
| ItemModelData | ItemModel     | .json        |
