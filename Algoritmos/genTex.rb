file="Resultados"

f = File.open(file,"r")
pobl=0
gen=0
pcruc=0
pmut=0
elit=0
reemp=""
selec=""
print "\\begin{center}\n\\begin{tabular}{cc}\nReglas&Error\\\\\\hline\n"
f.readlines.join.split("NumReglas : ")[1..-1].each do  |line|
	reg = line.split[0].to_i
	pobl = line.split[3].to_i
	gen = line.split[5].to_i
	pcruc = line.split[8][0..-2]
	pmut = line.split[10][0..-2]
	elit = line.split[12]
	selec = line.split[15]
	reemp = line.split[17][0..-8]

	err = line.split[19][1..-2].to_f

	print "#{reg} & #{format("%.3f\\%%",err*100)}\\\\\n"
end
print "\\end{tabular}\n\\caption{\#Individuos: #{pobl},Generaciones: #{gen}, Elitismo: #{elit}\\%, PCruce: #{pcruc}\\%, PMut: #{pmut}\\%, Reemplazo: #{reemp}, Selecci\\'on: #{selec}}\n\\end{center}\n"

f.close();
