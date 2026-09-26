# Caso #1 - Mutant Battle
___
## Descripción General
___
El proyecto consiste en una simulación automática de batalla entre dos equipos de mutantes. El usuario únicamente especifica el tamaño de los equipos (entre 3 y 11); a partir de ese momento, el juego genera los atributos y poderes de cada participante e inicia la contienda de manera totalmente autónoma hasta que un equipo resulte ganador.

La solución se encuentra estructurada en cuatro capas principales, junto con un paquete de utilidades que aloja el patrón Observer e infraestructura general:

* **Model Layer (model)**: Define a los mutantes, sus características e implementación de poderes.
* **Game Layer (game)**: Modela el campo de batalla no visual, gestionando las estadísticas de combate y marcador de los equipos.
* **Control Layer (control)**: Administra el desplazamiento, concurrencia por hilos y las interacciones/combates por proximidad entre mutantes.
* **UI Layer (iu)**: Visualiza en tiempo real la simulación gráfica a través del patrón MVC y el patrón Observer.
* **Utilities (util)**: Proporciona el mecanismo Observable/Observer y almacena constantes globales.

---
## Especificación de Objetos (Spec)
___
### Patrón Observer y Utilidades (util)

#### IObserver (Interfaz)
* **Métodos:**
  * update(Object source, Observable ob): void — Notifica al observador cuando ocurre un cambio en el objeto observado.

#### Observable (Clase Abstracta)
* **Atributos:**
  * - observers: Vector<IObserver> — Colección de observadores suscritos.
* **Métodos:**
  * + addObserver(IObserver ob): void
  * + removeObserver(IObserver ob): void
  * + notifyObservers(Object source): void — Invoca update(source, this) sobre cada observador registrado.

#### Constantes
Clase no instanciable que concentra todos los valores constantes y configuraciones del juego.
* **Variables principales:**
  * Atributos de Mutantes: ENERGIA_INICIAL (100), DEFENSA_MIN (1), DEFENSA_MAX (3), DANIO_MIN (1), DANIO_INICIAL_MAX (3), DANIO_MAX (7), INCREMENTO_DANIO (1).
  * Configuración del Campo: TAM_EQUIPO_MIN (3), TAM_EQUIPO_MAX (11), BORDE_X, BORDE_Y, RADIO.
  * Aspectos Gráficos: COLOR_EQUIPO_A, COLOR_EQUIPO_B, SIMBOLO_EQUIPO_A, SIMBOLO_EQUIPO_B, REFRESCO_UI_MS.

---

### 1. Model Layer (model)

#### Mutante (Hereda de Observable)
Clase que representa a un individuo mutante dentro del juego.
* **Atributos:**
  * - id: int
  * - energia: int
  * - defensa: int
  * - ataque: int
  * - velocidad: double
  * - visibilidad: boolean
  * - Coordenada: Point
  * - poderesMutantes: Vector<IPoderMutante>
* **Métodos:**
  * + mover(Point nPosition): void — Cambia la ubicación del mutante y dispara notificaciones.
  * + getPos(): Point
  * + addEnergia(int valor): void / + getEnergia(): int
  * + addAtaque(int valor): void / + getAtaque(): int
  * + addDefensa(int valor): void / + getDefensa(): int
  * + setVelocidad(double valor): void / + getVelocidad(): double
  * + changeVisibilidad(): void / + getVisibilidad(): boolean
  * + usarPoderMutante(): boolean
  * + estaVivo(): boolean — Retorna verdadero si la energía es mayor a 0.
  * + getId(): int

#### IPoderMutante (Interfaz)
* **Métodos:**
  * ActivarPoder(Mutante a): boolean

#### Poderes (Implementan IPoderMutante):
* PoderAtaque: Aumenta el poder ofensivo del mutante.
* PoderDefensa: Otorga resistencia o bonificación defensiva.
* PoderInvisibilidad: Alterna el estado de visibilidad del mutante.
* PoderVelocidad: Incrementa la velocidad de desplazamiento.
* PoderRecarga: Restaura un valor de energía al mutante.

#### MainModel
Prueba unitaria independiente para verificar el comportamiento de los objetos de la capa Model.

---

### 2. Game Layer (game)

#### ConfigBattleField
Objeto de transferencia de datos con las especificaciones del campo.
* **Atributos:**
  * - borde: Point
  * - tamEquipo: int
  * - radio: double
* **Métodos:**
  * + getBorde(): Point, + getTamEquipo(): int, + getRadio(): double

#### BattleField (Hereda de Observable, Implementa IObserver)
Administra el estado lógico del terreno de combate.
* **Atributos:**
  * - terminado: AtomicBoolean
  * - config: ConfigBattleField
  * - mutantesA: Vector<Mutante>
  * - mutantesB: Vector<Mutante>
* **Métodos:**
  * + agregarMutanteA(Mutante mutante): void / + agregarMutanteB(Mutante mutante): void
  * + hayGanador(): boolean
  * + getGanador(): String
  * + getVivosA(): int / + getVivosB(): int
  * + getMuertosA(): int / + getMuertosB(): int
  * + update(Object source, Observable ob): void — Monitorea cambios en mutantes para actualizar recuentos de vivos/muertos.

#### MainGame
Prueba unitaria independiente para validar la adición de equipos y la lógica del marcador.

---

### 3. Control Layer (control)

#### ControladorBattleField (Implementa Runnable, IObserver)
Gestor del combate concurrente y detección de proximidad.
* **Atributos:**
  * - battleField: BattleField
  * - hilosMutantes: Vector<MutanteThread>
  * - paresEnRadio: Set<String> — Rastrea pares de enemigos cercanos para no repetir encuentros instantáneos.
  * - activo: boolean
* **Métodos:**
  * + crearEquipos(int tamEquipo): void
  * + iniciarMovimiento(): void — Asigna y ejecuta un hilo MutanteThread por cada integrante.
  * + run(): void
  * + update(Object source, Observable ob): void — Escucha el desplazamiento de cada mutante para llamar a verificarRadio.
  * + verificarRadio(Mutante mutante): void — Evalúa la distancia respecto a rivales.
  * - resolverEncuentro(Mutante a, Mutante b): void — Determina si atacan o defienden.
  * - aplicarAtaque(Mutante atacante, Mutante objetivo, boolean objetivoDefiende): void — Modifica la energía del rival y aumenta el ataque del atacante.
  * + detener(): void

#### MutanteThread (Hereda de Thread)
Hilo encargado del desplazamiento individual de cada mutante.
* **Atributos:**
  * - mutante: Mutante
  * - config: ConfigBattleField
  * - activo: boolean
  * - dirX: double, - dirY: double
  * - cooldownTicks: int
* **Métodos:**
  * + run(): void — Bucle de movimiento continuo según la velocidad del mutante.
  * - mover(): void
  * + detener(): void

#### MainControl
Prueba unitaria independiente para verificar el funcionamiento de los hilos y combate por consola.

---

### 4. UI Layer (iu)

#### BattleController (Implementa IObserver)
Controlador dentro del esquema MVC gráfico.
* **Atributos:**
  * - controlador: ControladorBattleField
  * - battleField: BattleField
  * - interfase: Interfase
  * - refresco: Timer
* **Métodos:**
  * + iniciarBatalla(int tamEquipo): void
  * + nuevaBatalla(): void — Permite iniciar una nueva partida tras finalizar.
  * + update(Object source, Observable ob): void
  * + mostrar(BattleField battleField): void

#### Interfase (Hereda de JFrame)
Ventana principal de la interfaz de usuario.
* **Atributos:**
  * - controller: BattleController
  * - panel: PanelBattleField
  * - anchoPantalla: int, - altoPantalla: int
* **Métodos:**
  * + pedirTamEquipo(): int
  * + refrescar(): void
  * + mostrarGanador(String equipoGanador): void

#### PanelBattleField (Hereda de JPanel)
Canvas visual para el renderizado del campo de batalla.
* **Métodos:**
  * # paintComponent(Graphics g): void — Dibuja mutantes vivos, barras de energía, símbolos de equipo y marcador.
  * - dibujarEquipo(Graphics2D g2, Vector<Mutante> equipo, Color color, String simbolo, int tam): void
  * - dibujarMarcador(Graphics2D g2, BattleField bf): void

#### MainIU / Main
Puntos de entrada principales para probar la UI aislada y para la ejecución completa del juego.

---
## Diagrama UML
___
El diagrama UML fue generado gracias a la herramienta PlantUML-Ecliplse encontrada en el repositorio [github de plantuml-eclipse](https://github.com/plantuml/plantuml-eclipse) y a un tutorial encontrado en [Youtube](https://www.youtube.com/watch?v=8BAP1O8DQcE).

```
@startuml

package "util" {
    interface IObserver {
        +update(source: Object, ob: Observable): void
    }

    abstract class Observable {
        -observers: Vector<IObserver>
        +addObserver(ob: IObserver): void
        +removeObserver(ob: IObserver): void
        +notifyObservers(source: Object): void
    }

    class Constantes {
        +{static} ENERGIA_INICIAL: int
        +{static} DEFENSA_MIN: int
        +{static} DEFENSA_MAX: int
        +{static} DANIO_MIN: int
        +{static} DANIO_INICIAL_MAX: int
        +{static} DANIO_MAX: int
        +{static} INCREMENTO_DANIO: int
        +{static} CANTIDAD_PODERES: int
        +{static} AUMENTO_DEFENSA: int
        +{static} AUMENTO_ATAQUE: int
        +{static} AUMENTO_VELOCIDAD: float
        +{static} AUMENTO_CURA: int
        +{static} DURACION_PODER: float
        +{static} TAM_EQUIPO_MIN: int
        +{static} TAM_EQUIPO_MAX: int
        +{static} BORDE_X: int
        +{static} BORDE_Y: int
        +{static} RADIO: double
        +{static} NOMBRE_EQUIPO_A: String
        +{static} NOMBRE_EQUIPO_B: String
        +{static} COLOR_EQUIPO_A: Color
        +{static} COLOR_EQUIPO_B: Color
        +{static} SIMBOLO_EQUIPO_A: String
        +{static} SIMBOLO_EQUIPO_B: String
        +{static} VELOCIDAD_MIN: double
        +{static} VELOCIDAD_MAX: double
        +{static} PASO_MOVIMIENTO: double
        +{static} PROBABILIDAD_ATAQUE: double
        +{static} EVENTO_MOVIMIENTO: String
        +{static} EVENTO_MUERTE: String
        +{static} EVENTO_FIN: String
        +{static} TICK_MOVIMIENTO_MS: long
        +{static} COOLDOWN_TICKS: int
        +{static} REFRESCO_UI_MS: long
        +{static} TITULO_VENTANA: String
        +{static} TEXTO_BOTON_NUEVA_BATALLA: String
        +{static} COLOR_FONDO: Color
        +{static} COLOR_TEXTO: Color
        +{static} COLOR_ENERGIA: Color
        +{static} COLOR_ENERGIA_FONDO: Color
        +{static} PROPORCION_TAM_MUTANTE: double
        +{static} TAM_MUTANTE_MIN: int
        +{static} PROPORCION_FUENTE: double
        +{static} ALTO_BARRA_ENERGIA: int
        +{static} SEPARACION_BARRA: int
        +{static} MARGEN_MARCADOR: int
        +{static} TAM_FUENTE_MARCADOR: int
        +{static} TAM_EQUIPO_PRUEBA: int
        -Constantes()
    }

    Observable "1" --> "*" IObserver : observers
}

package "model" {
    interface IPoderMutante {
        +ActivarPoder(a: Mutante): boolean
    }

    class Mutante {
        -id: int
        -energia: int
        -defensa: int
        -ataque: int
        -velocidad: double
        -visibilidad: boolean
        -Coordenada: Point
        +Mutante(pId: int, pDefensa: int, pAtaque: int, pVelocidad: double, pCoordenada: Point, pPoderesAleatorios: Vector<IPoderMutante>)
        +mover(nPosition: Point): void
        +getPos(): Point
        +addEnergia(valor: int): void
        +getEnergia(): int
        +addAtaque(valor: int): void
        +getAtaque(): int
        +addDefensa(valor: int): void
        +getDefensa(): int
        +setVelocidad(valor: double): void
        +getVelocidad(): double
        +changeVisibilidad(): void
        +getVisibilidad(): boolean
        +usarPoderMutante(): boolean
        +estaVivo(): boolean
        +getId(): int
    }

    class PoderAtaque implements IPoderMutante {
        +PoderAtaque()
        +ActivarPoder(mutante_n: Mutante): boolean
    }

    class PoderDefensa implements IPoderMutante {
        +PoderDefensa()
        +ActivarPoder(mutante_n: Mutante): boolean
    }

    class PoderInvisibilidad implements IPoderMutante {
        +PoderInvisibilidad()
        +ActivarPoder(mutante_n: Mutante): boolean
    }

    class PoderVelocidad implements IPoderMutante {
        +PoderVelocidad()
        +ActivarPoder(mutante_n: Mutante): boolean
    }

    class PoderRecarga implements IPoderMutante {
        +PoderRecarga()
        +ActivarPoder(mutante_n: Mutante): boolean
    }

    class MainModel {
        +{static} main(args: String[]): void
        -{static} crearPoderAleatorio(): Vector<IPoderMutante>
        -{static} imprimirMutante(m: Mutante): void
    }

    util.Observable <|-- Mutante
    Mutante "1" --> "*" IPoderMutante : poderesMutantes
}

package "game" {
    class ConfigBattleField {
        -borde: Point
        -tamEquipo: int
        -radio: double
        +ConfigBattleField()
        +ConfigBattleField(tamEquipo: int, ancho: int, alto: int)
        +getBorde(): Point
        +getTamEquipo(): int
        +getRadio(): double
    }

    class BattleField {
        -terminado: AtomicBoolean
        +BattleField(config: ConfigBattleField)
        +agregarMutanteA(mutante: Mutante): void
        +agregarMutanteB(mutante: Mutante): void
        +hayGanador(): boolean
        +getGanador(): String
        +update(source: Object, ob: Observable): void
        +getVivosA(): int
        +getVivosB(): int
        +getMuertosA(): int
        +getMuertosB(): int
        -contarVivos(equipo: Vector<Mutante>): int
    }

    class MainGame {
        +{static} main(args: String[]): void
        -{static} crearMutanteAleatorio(id: int): Mutante
        -{static} crearPoderAleatorio(): Vector<IPoderMutante>
    }

    util.Observable <|-- BattleField
    util.IObserver <|.. BattleField
    BattleField "1" --> "1" ConfigBattleField : config
    BattleField "1" --> "*" model.Mutante : mutantesA
    BattleField "1" --> "*" model.Mutante : mutantesB
}

package "control" {
    class MutanteThread {
        -activo: boolean
        -dirX: double
        -dirY: double
        -cooldownTicks: int
        +MutanteThread(mutante: Mutante, config: ConfigBattleField)
        +run(): void
        -mover(): void
        +detener(): void
    }

    class ControladorBattleField {
        -paresEnRadio: Set<String>
        -activo: boolean
        +ControladorBattleField(battleField: BattleField)
        +crearEquipos(tamEquipo: int): void
        -crearMutanteAleatorio(id: int): Mutante
        +iniciarMovimiento(): void
        +run(): void
        +detener(): void
        +update(source: Object, ob: Observable): void
        +verificarRadio(mutante: Mutante): void
        -resolverEncuentro(a: Mutante, b: Mutante): void
        -aplicarAtaque(atacante: Mutante, objetivo: Mutante, objetivoDefiende: boolean): void
        -decideAtacar(): boolean
        -enemigosDe(mutante: Mutante): Vector<Mutante>
        -clavePar(a: Mutante, b: Mutante): String
    }

    class MainControl {
        -{static} INTERVALO_MARCADOR_MS: long
        +{static} main(args: String[]): void
        -{static} imprimirEquipo(nombre: String, battleField: BattleField, esEquipoA: boolean): void
    }

    Thread <|-- MutanteThread
    Runnable <|.. ControladorBattleField
    util.IObserver <|.. ControladorBattleField
    MutanteThread "1" --> "1" model.Mutante : mutante
    MutanteThread "1" --> "1" game.ConfigBattleField : config
    ControladorBattleField "1" --> "1" game.BattleField : battleField
    ControladorBattleField "1" --> "*" MutanteThread : hilosMutantes
}

package "iu" {
    class BattleController {
        -refresco: Timer
        +setInterfase(interfase: Interfase): void
        +iniciarBatalla(tamEquipo: int): void
        +nuevaBatalla(): void
        +update(source: Object, ob: Observable): void
        +mostrar(battleField: BattleField): void
    }

    class Interfase {
        -anchoPantalla: int
        -altoPantalla: int
        +Interfase(controller: BattleController)
        +mostrarPantallaCompleta(): void
        +getAnchoCampo(): int
        +getAltoCampo(): int
        +pedirTamEquipo(): int
        +refrescar(): void
        +mostrarGanador(equipoGanador: String): void
    }

    class PanelBattleField {
        +PanelBattleField(controller: BattleController)
        #paintComponent(g: Graphics): void
        -calcularTamMutante(): int
        -dibujarEquipo(g2: Graphics2D, equipo: Vector<Mutante>, color: Color, simbolo: String, tam: int): void
        -dibujarMarcador(g2: Graphics2D, bf: BattleField): void
    }

    class MainIU {
        +{static} main(args: String[]): void
        -{static} crearMutanteDePrueba(id: int, config: ConfigBattleField): Mutante
    }

    util.IObserver <|.. BattleController
    JFrame <|-- Interfase
    JPanel <|-- PanelBattleField
    BattleController "1" --> "1" control.ControladorBattleField : controlador
    BattleController "1" --> "1" game.BattleField : battleField
    BattleController "1" --> "1" Interfase : interfase
    Interfase "1" --> "1" BattleController : controller
    Interfase "1" --> "1" PanelBattleField : panel
    PanelBattleField "1" --> "1" BattleController : controller
}

class Main {
    +{static} main(args: String[]): void
}
@endl
```


![Diagrama UML](PlantUML.png)
