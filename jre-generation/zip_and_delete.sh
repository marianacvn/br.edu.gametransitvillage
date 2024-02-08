#!/bin/bash

# Caminho para as pastas
LINUX_DIR="linux/transit_village_jre_linux"
WINDOWS_DIR="windows/transit_village_jre_windows"

# Nome dos arquivos zip
LINUX_ZIP="linux/transit_village_jre_linux.zip"
WINDOWS_ZIP="windows/transit_village_jre_windows.zip"

# Navegar até o diretório linux, compactar a pasta e voltar para o diretório original
cd linux
zip -r transit_village_jre_linux.zip transit_village_jre_linux
cd ..

# Navegar até o diretório windows, compactar a pasta e voltar para o diretório original
cd windows
zip -r transit_village_jre_windows.zip transit_village_jre_windows
cd ..

# Excluir as pastas
rm -rf $LINUX_DIR
rm -rf $WINDOWS_DIR