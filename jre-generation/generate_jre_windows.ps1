# Caminho para o diretório javafx-jmods
$FX_PATH = Join-Path -Path $PWD -ChildPath "windows\javafx-jmods-17.0.9"

# Caminho para o diretório de dependências
$DEP_PATH = Join-Path -Path $PWD -ChildPath "..\target\dependency"

# Caminho para o arquivo jar
$JAR_PATH = Join-Path -Path $PWD -ChildPath "..\target\transit-village-1.0-SNAPSHOT-jar-with-dependencies.jar"

# Caminho para o diretório de saída
$OUTPUT_DIR = Join-Path -Path $PWD -ChildPath "windows\transit_village_jre_windows"

# Se o diretório de saída existir, exclua-o
if (Test-Path -Path $OUTPUT_DIR) {
    Remove-Item -Path $OUTPUT_DIR -Recurse -Force
}

# Comando jlink
jlink --module-path "$FX_PATH;$DEP_PATH" --add-modules java.base,java.logging,java.xml,java.sql,java.desktop,java.scripting,jdk.unsupported,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web --output $OUTPUT_DIR

# Criar o diretório bin se não existir
$BIN_PATH = Join-Path -Path $OUTPUT_DIR -ChildPath "bin"
if (-not (Test-Path -Path $BIN_PATH)) {
    New-Item -Path $BIN_PATH -ItemType Directory
}
# Copiar o arquivo jar para o diretório bin da JRE personalizada
Copy-Item -Path $JAR_PATH -Destination $BIN_PATH

# Criar o script execute.bat
$BAT_CONTENT = @"
@echo off
set "appdir=%~dp0"
"%appdir%\bin\java.exe" -Dprism.order=sw -jar "%appdir%\bin\transit-village-1.0-SNAPSHOT-jar-with-dependencies.jar"
"@
$BAT_PATH = Join-Path -Path $OUTPUT_DIR -ChildPath "execute.bat"
$BAT_CONTENT | Out-File -FilePath $BAT_PATH -Encoding default