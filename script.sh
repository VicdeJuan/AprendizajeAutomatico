gawk -F "," '
	BEGIN{
		t=0
		numeros["+"]=0
		numeros["-"]=0
	}
	NR==2{
		for (i=1;i<=NF;i++){
			nombre[i]=$i
		}
	}
	NR==3{
		for (i=1;i<=NF;i++){
			tipo[i]=$i
		}
	}
	NR>3{

		if(index($0,"?")==0){
			for (i=1;i<NF;i++){
				if(tipo[i]=="Continuo"){
					split($NF,clas,"\n")
					cad=nombre[i]";"clas[1]
					split(cad,fin,"\r")
					if(fin[1] in sum) {
						sum[fin[1]]+=$i
						sum2[fin[1]]+=$i*$i
						tot[fin[1]]+=1
					}
					else{
						sum[fin[1]]=$i
						sum2[fin[1]]=$i*$i
						tot[fin[1]]=1
					}

				}
				else{
					split($NF,clas,"\n")
					cad=nombre[i]$i";"clas[1]
					split(cad,fin,"\r")
					if(fin[1] in mapa) {
						mapa[fin[1]]+=1
						
					}
					else{
						mapa[fin[1]]=1
						
					}
				}
				
			}
			split($NF,clas,"\n")
			split(clas[1],clase,"\r")
			numero[clase[1]]+=1
			t=t+1
		}
	}
	END{
		print numero["+"]" "numero["-"]
		for(s in mapa){
			split(s,clas,";")
			print s" - "mapa[s]/numero[clas[2]]
			#print s" - "sum[s]/tot[s]" - "(sum2[s]/tot[s]-sum[s]/tot[s]*sum[s]/tot[s])
		}
		for(s in sum){
			#split(s,clas,";")
			#print s" - "mapa[s]/numero[clas[2]]
			print s" - "sum[s]/tot[s]" - "(sum2[s]/tot[s]-sum[s]/tot[s]*sum[s]/tot[s])
		}
	}



' apauto/crx.data > salida.txt