file=ARGV[0]
outputdir=ARGV[1]

f = File.open(file, "r")
a = [0,0,0,0]
b=[[]]
i=0
j=0
f.readlines().each do |line|
	val = line.split(":")[1].to_f
	a[i] = val
	i=(i+1)%4
	if i==0
		b[j] = a
		j+=1
	end
end

tow_file = outputdir + "/"+file.split(".")[0].split("/")[1]
tow = File.open(tow_file,'w')

tow.print "max medio min\n"
b.map { |a| tow.print "#{a[1]} #{a[2]} #{a[3]}\n"}

f.close()
tow.close()
