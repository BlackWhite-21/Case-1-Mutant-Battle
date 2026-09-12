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
@startuml DiagramaMutantes

skinparam classAttributeIconSize 0
skinparam monochrome false
skinparam shadowing false

class Cordenada {
  - x : int
  - y : int
}

class Mutante {
  - energia : int
  - defensa : int
  - ataque : int
  - cooldown : time
  - velocidad : double
  - visibilidad : bool
  - cordenadas : Cordenada
  - poderesMutantes : Vector<PoderMutante>
  --
  + moverse(posicion : Cordenada) : void
  + atacar(mutante : Mutante) : void
  + defenderse(mutante : Mutante) : void
  + usarPoderMutante() : void
}

abstract class PoderMutante {
  + {abstract} activarPoder(mutante : Mutante) : void
}

class PoderDefensa {
  - aumentoDefensa : int
  - duracion : float
  --
  + activarPoder(mutante : Mutante) : void
}

class PoderAtaque {
  - aumentoAtaque : int
  - duracion : float
  --
  + activarPoder(mutante : Mutante) : void
}

class PoderInvisibilidad {
  - duracion : float
  --
  + activarPoder(mutante : Mutante) : void
}

class PoderVelocidad {
  - duracion : float
  - aumentoVelocidad : float
  --
  + activarPoder(mutante : Mutante) : void
}

class PoderCura {
  - aumentoCura : int
  - duracion : float
  --
  + activarPoder(mutante : Mutante) : void
}

class BattleField {
  - borde : Cordenada
  - tamEquipo : int
  - mutantesA : Vector<Mutante>
  - mutantesB : Vector<Mutante>
  --
  + crearEquipos(tamEquipo : int) : void
  + iniciarMovimiento() : void
  + verificarRadio() : void
}

' ---- Relaciones ----

PoderMutante <|-- PoderDefensa
PoderMutante <|-- PoderAtaque
PoderMutante <|-- PoderInvisibilidad
PoderMutante <|-- PoderVelocidad
PoderMutante <|-- PoderCura

Mutante "1" *-- "1" Cordenada : cordenadas
Mutante "1" o-- "0..*" PoderMutante : poderesMutantes

BattleField "1" *-- "1" Cordenada : borde
BattleField "1" o-- "0..*" Mutante : mutantesA
BattleField "1" o-- "0..*" Mutante : mutantesB

@enduml
