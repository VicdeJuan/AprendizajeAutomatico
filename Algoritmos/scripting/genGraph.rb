file=ARGV[0]
outputdir=ARGV[1]

f = File.open(file, "r")
a = [0,0,0,0]
i=0
j=0
tow_file = outputdir + "/"+file.split(".")[0].split("/")[1]
tow = File.open(tow_file,'w')
tow.print "max medio min\n"
f.readlines().each do |line|
	val = line.split(":")[1].to_f
	a[i] = val
	i=(i+1)%5
	if i==0
		j+=1
		tow.print "#{a[1]} #{a[2]} #{a[3]}\n"
	end
end

f.close()
tow.close()
