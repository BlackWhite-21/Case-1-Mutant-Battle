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

PoderDefensa
* reduccionDano float
* duracion float
* Methods:
    * bloquear(float danoRecibido) float

PoderAtaque
* danoBase float
* alcance float
* Methods:
      * calcularDano() float

Poder
* nombre text

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