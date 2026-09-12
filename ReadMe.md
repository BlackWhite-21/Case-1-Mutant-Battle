## Caso #1 - Mutant Battle




## Spec de los objetos

Mutante
* energia
* poderMutante
* defensa
* ataque
* moverse()
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
* position
* mutantesA
* mutantesB
* crearEquipos()
* iniciarMovimiento()


## Diagrama UML en PlantUML 