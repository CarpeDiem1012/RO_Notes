## @ 关于 Git 的行尾转换提示 -- LF 和 CRLF 的前世今生
- LF = Line Feed，即换行符 \n
- CR = Carriage Return，即回车符 \r
- 在古早的打字机时代，两者都有特别的意义。LF 代表的是让打字机头维持位置不变，然后将纸张向上挪动一行。CR 代表保持纸张不动，直接将打字机头移至当前行行首。所以在当时，一个完整的“换行”操作需要 CR+LF 才可以实现。
- 如今，Linux/Unix 系统使用 LF 作为 行结尾（EoF）；Win 系统则使用 CRLF。当使用 version control 工具跨平台工作时，就会出现不兼容的 conflict。
- 可以使用 `core.autocrlf` 配置告诉 git 如何处理系统上的行尾。可以通过以下命令完成
  ``` shell
  git config --global core.autocrlf [true|false|input]
  ```
  - false：一般不使用
  - true：拉取到本地的版本自动转换为 CRLF，但推送到远程的版本永远是 LF（Win系统推荐使用）
  - input：拉取到本地的版本保持不变，推送到远程的版本永远是 LF