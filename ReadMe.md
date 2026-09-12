## Caso #1 - Mutant Battle


## Spec de los objetos

Mutante
* energia int
* defensa int
* ataque int
* cooldown time
* velocidad double
* visibilidad bool
* cordenadas Cordenada
* poderesMutantes Vector < PoderMutante >
* Metodos:
  * moverse(Cordenada posicion)
  * atacar(Mutante mutante)
  * defenderse(Mutante mutante)
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
* borde Cordenada
* tamEquipo int
* mutantesA vector < Mutante >
* mutantesB vector < Mutante >
* Metodos:
  * crearEquipos(tamEquipo)
  * iniciarMovimiento()
  * verificarRadio()

Cordenada
* x
* y


## Diagrama UML en PlantUML 