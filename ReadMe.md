## Caso #1 - Mutant Battle


## Spec de los objetos

Mutante
* energia
* defensa
* ataque
* cordenadas
* poderesMutantes
* Metodos:
  * moverse(Cordenada posicion)
  * atacar(Mutante)
  * defenderse(Mutante)
  * usarPoderMutante()

PoderMutante
* tipo text (fisico / energia)
* duracion float

PoderDefensa
* reduccionDano float
* duracion float
* Methods:
    * bloquear(float danoRecibido) float

PoderAtaque
* danoBase float
* alcance float
* duracion float
* Methods:
      * calcularDano() float

PoderVisilidad:
* duracion float
* Detecccion boolean


PoderVelocidad
* duracion float
* multiplicadorVelocidad float

BattleField  
* bordes
* tamEquipo
* mutantesA
* mutantesB
* Metodos:
  * crearEquipos(tamEquipo)
  * iniciarMovimiento()
  

Cordenada
* x
* y


## Diagrama UML en PlantUML 