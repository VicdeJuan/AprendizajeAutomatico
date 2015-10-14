
valor=$(echo $1 | awk -v FS="\." '{print $1}')

gawk -F "," -v datos=$valor  '
	BEGIN{
		print "@relation "datos
		cl["Continuo"]="numeric"
	}
	NR==2{
		for(i=1;i<=NF;i++){
			if(i==NF){
			split($i,aux,"\r")
			nombres[i]=aux[1]
			continue
			}
			nombres[i]=$i
		}

		fin=NF
	}
	NR==3{
		for(i=1;i<NF;i++){

			tipos[i]=$i
		}


	}
	NR>3{
		data[NR]=$0
		if(!($NF in clases)){
			split($NF,aux,"\r")
			clases[aux[1]]=1
		}
		for(j=1;j<NF;j++){
			if(tipos[j]=="Nominal")
				valores[j][$j]=1
		}

	}
	END{
		for (n=1;n<=length(tipos);n++){

			if(tipos[n]=="Continuo")
				print "@attribute "nombres[n]" numeric"
			else{
				cad="@attribute "nombres[n]" {"
				f=0
				for(v in valores[n]){
					if(v=="?")
						continue
					if(f==0){
						cad=cad v
						f=1
					}
					else
						cad=cad","v
				}
				print cad"}"
			}
		}
		cad="@attribute "nombres[fin]" {"

		i=0
		for(c in clases){
			if(i==0){
				i=1
				cad=cad c
			}
			else
				cad=cad","c
		}
		print cad"}"
		print "@data"
		for(lin in data){
			print data[lin]
		}

	}' $1 > $2
