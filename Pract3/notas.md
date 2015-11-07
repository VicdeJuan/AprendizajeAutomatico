## Notas útiles
1 individuo -> 20 o 30 reglas.

Fitness = % de aciertos en el train.

- Cruce en un punto (pto interno de una regla o intercambio de reglas)
- Mutación (mutar un bit o eliminar/añadir una regla aleatoria)

- Hijos sustituyen a padres// Hijos sustituyen a los peores// ... // lo que sea

- Memoria con medidas de todo. Si se hace modular se puede incluso comparar.

## Train

Genero una población aleatoria. Para cada individuo clasifico los datos de train y obtengo el % de aciertos (fitness). Acabado el bucle: Muto/cruzo - reemplazo y vuelta a empezar.

## Cómo clasificar

Dado un individo, ¿cómo clasifico? El individuo tiene reglas. Por ejemplo,

individuo  = (rojo, si, clase = par) donde los atributos son: (rojo/verde/amarillo; si/no ; clase (par/impar))

1) Pasamos la información a bits -> individuo = (10,1) -> 101

1.1) Construimos reglas (un poco al tuntún):
	101 -> par
	**1 -> par
	100 -> par
	000 -> impar
	...
	default -> par

2) Cuando clasificamos. Sea un indiduo nuevo = (verde,no) -> 100. Miramos las reglas y cuadra con "100 -> par", con lo que asignamos la clase par.


## Para la práctica:

Utilizamos el conjunto de datos "crx.data" en el que todos son nominales. En este caso, la codificación en bits es más sencilla.

Como tenemos 7 atributos de 3 posibles valores, tendremos 21 reglas aprox. Habría que tener cuidado con reglas que no se cotradigan... Aunque si hay contradictorias y elegimos siempre la primera que nos encontremos dan igual las contradicciones. Salvo que un hijo herede la contradictoria... Le voy a escribir a Mingo a ver que dice.



