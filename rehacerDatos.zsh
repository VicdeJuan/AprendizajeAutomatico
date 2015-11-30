#!/bin/zsh

echo "Preparing"

cd Algoritmos
mkdir -p tex/img .toplot
cp scripting/* .
echo "Generating tables"
zsh genTablasTex.zsh
echo "Generating graphs"
zsh genGraphs.zsh

echo "Cleaning"
rm gen*
rm -r .toplot

cd ..
cp -r Algoritmos/tex ./Pract3/Memoria
echo "Lets compile..."
cd ./Pract3/Memoria

latexmk MemoriaP3.tex
