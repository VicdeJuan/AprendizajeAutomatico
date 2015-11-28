file="Resultados"

f = File.open(file,"r")
pobl=0
gen=0
pcruc=0
pmut=0
elit=0
reemp=""
selec=""
print "\\begin{center}\n\\begin{tabular}{cccccc}\nReemplazo & Seleccion & Poblacion & Generaciones & Error\\\\\\hline\n"

f.readlines.join.split("Apartado 2:")[1].split("Valores:")[1..-1].each do  |line|
	pobl = line.split()[1]
	gen = line.split()[3]

	reemp = line.split()[13]
	selec = line.split()[15]
	err = line.split()[17][1..-2]

	print "#{reemp} & #{selec} & #{pobl} & #{gen} & #{format("%.3f\\%",err.to_f*100)} \\\\\n"
end
print "\\end{tabular}\n\\end{center}\n"

f.close();
