#!/bin/bash

# Caminho para o diretório javafx-jmods
FX_PATH="linux/javafx-jmods-17.0.9"

# Caminho para o diretório de dependências
DEP_PATH="../target/dependency"

# Caminho para o arquivo jar
JAR_PATH="../target/transit-village-1.0-SNAPSHOT-jar-with-dependencies.jar"

# Caminho para o diretório de saída
OUTPUT_DIR="linux/transit_village_jre_linux"

# Se o diretório de saída existir, exclua-o
if [ -d "$OUTPUT_DIR" ]; then
    rm -rf "$OUTPUT_DIR"
fi

# Comando jlink
jlink --module-path "$FX_PATH:$DEP_PATH" --add-modules java.base,java.logging,java.xml,java.sql,java.desktop,java.scripting,jdk.unsupported,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web --output $OUTPUT_DIR

# Copiar o arquivo jar para o diretório bin da JRE personalizada
cp $JAR_PATH $OUTPUT_DIR/bin/

# Criar o script execute.sh
echo '#!/bin/sh
      appdir=$(dirname "$(readlink -f "$0")")
      "$appdir/bin/java" -Dprism.order=sw -Dprism.verbose=true -jar "$appdir/bin/transit-village-1.0-SNAPSHOT-jar-with-dependencies.jar"' > $OUTPUT_DIR/execute.sh

# Adicionar permissões de execução ao script execute.sh
chmod +x $OUTPUT_DIR/execute.sh