# source tools/env.sh  (dat PATH/DOTNET cho cong cu trong work/tools)
_R="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
export DOTNET_ROOT="$_R/work/tools/dotnet" DOTNET_ROLL_FORWARD=Major DOTNET_CLI_TELEMETRY_OPTOUT=1
export PATH="$_R/work/tools/android-15:$DOTNET_ROOT:$PATH"
alias il2cppdumper='dotnet '"$_R"'/work/tools/il2cppdumper/Il2CppDumper.dll'
alias apkeditor='java -jar '"$_R"'/work/tools/APKEditor-1.4.9.jar'
alias apktool='java -jar '"$_R"'/work/tools/apktool_2.12.0.jar'
