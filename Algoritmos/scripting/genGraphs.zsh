output_plot=".toplot"

for d in $(ls res/*);
do
	sed 's/,/\./g' $d > /tmp/aux
	cat /tmp/aux > $d"_point"
done

for d in $(ls res/*point);
do
	ruby genGraph.rb $d $output_plot
done

for d in $(ls $output_plot);
do
	gnuplot -e "filename='$output_plot/$d';outpng='tex/img/$d.png'" plotMMM.gp
done
