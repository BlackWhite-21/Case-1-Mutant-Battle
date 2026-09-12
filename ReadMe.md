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
* Metodos:
     * activarPoder(Mutante mutante)

PoderDefensa
* aumentoDefensa int
* duracion float
* override: 
    * activarPoder(Mutante mutante)

PoderAtaque
* aumentoAtaque int
* duracion float
* override: 
    * activarPoder(Mutante mutante)

PoderInvisilidad:
* duracion float
* override: 
    * activarPoder(Mutante mutante)

PoderVelocidad
* duracion float
* aumentoVelocidad float
* override: 
    * activarPoder(Mutante mutante)

PoderCura
* aumentoCura int
* duracion float
* override: 
    * activarPoder(Mutante mutante)

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