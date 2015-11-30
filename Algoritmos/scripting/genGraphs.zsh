output_plot=".toplot"

for d in $(ls res/*);
do
	ruby genGraph.rb $d $output_plot
done

for d in $(ls $output_plot);
do
	gnuplot -e "filename='$output_plot/$d';outpng='tex/img/$d.png'" plotMMM.gp
done
