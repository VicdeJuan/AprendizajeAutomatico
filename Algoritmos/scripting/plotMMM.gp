set term png
set key outside
set output outpng
plot for [col=1:3] filename using 0:col with lines title columnheader

