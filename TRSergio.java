import java.util.Random;
import java.util.Scanner;

public class TRSergio {
    /**
     * Metodo para preguntar si el jugador quiere volver a jugar
     * @return true si quiere jugar de nuevo, false si no
     */
    private static boolean jugarDeNuevo() {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Quieres jugar de nuevo? (s/n)");
        String respuesta = sc.next();
        return respuesta.equalsIgnoreCase("s");
    }

    /**
     * Funcion para crear nuevo personaje. Crearemos una guerrera, un bandido o un marginado
     * @return personaje
     */
    private static Personaje crearPersonaje() {
        Scanner sc = new Scanner(System.in);
        Personaje p1;
        final int GUERRERA = 1;
        final int BANDIDO = 2;
        final int MARGINADO = 3;
        final int MAGO = 4;

        System.out.println("--- Se crea personaje ---");
        System.out.println("---Menu---");
        System.out.println("1. Guerrera");
        System.out.println("2. Bandido");
        System.out.println("3. Marginado");
        System.out.println("4. Mago");

        int opcion;
        do {
            System.out.println("Introduce una opcion: ");
            opcion = sc.nextInt();
        } while (opcion < GUERRERA || opcion > MAGO);

        if (opcion == GUERRERA) 
            p1 = new Personaje("Guerrera", "guerrera", 1500, 200, 100);
        else if (opcion == BANDIDO) 
            p1 = new Personaje("Bandido", "bandido", 1300, 300, 60);
        else if (opcion == MARGINADO) 
            p1 = new Personaje("Marginado", "marginado", 800, 200, 80);
        else 
            p1 = new Personaje("Mago", "mago", 700, 100, 50, 500); // El mago tiene 500 de maná

        System.out.println("Se ha creado el personaje correctamente");
        return p1;
    }

    /**
     * Función para realizar una acción durante el turno del jugador.
     * Puede devolver un clon si se crea durante el turno.
     * 
     * @param pj El personaje principal.
     * @param artorias El enemigo.
     * @param clon El clon actual (puede ser null si no existe).
     * @return El clon actualizado (puede ser null o un nuevo clon).
     */
    private static Personaje accion(Personaje pj, Personaje artorias, Personaje clon) {
        Scanner sc = new Scanner(System.in);
        final int ATACAR = 1;
        final int BEBER = 2;
        final int BEBER_ESTUS_MANA = 3;
        final int CREAR_LAGRIMA = 4; // Nueva opción para el mago.
        int clonCreado=0;

        System.out.println("--- Menú ---");
        System.out.println("1. Atacar");
        System.out.println("2. Beber");
        int maxOpciones = BEBER;
    
        if (pj.getClase().equalsIgnoreCase("mago") && clon == null && clonCreado==0) {
            System.out.println("3. Consumir estus de maná");
            System.out.println("4. Crear lágrima mimética");
            maxOpciones = CREAR_LAGRIMA;
        } else {
            System.out.println("3. Consumir estus de maná");
            maxOpciones = BEBER_ESTUS_MANA;
        }
    
        int opcion;
        do {
            System.out.println("Introduce una opción: ");
            while (!sc.hasNextInt()) { // Manejo de errores por entrada inválida.
                System.out.println("Por favor, introduce un número válido.");
                sc.next();
            }
            opcion = sc.nextInt();
        } while (opcion < ATACAR || opcion > maxOpciones);
    
        switch (opcion) {
            case ATACAR:
                if (pj.getClase().equalsIgnoreCase("mago") && clonCreado==0) {
                    if (pj.getMana() >= 50) {
                        artorias.recibirGolpe(pj.atacarConBaston());
                        pj.gastarMana(50);
                        System.out.println("Has atacado con el bastón. El maná restante es " + pj.getMana() + " puntos.");
                    } else
                        System.out.println("No tienes suficiente maná para atacar con el bastón.");
                } else {
                    artorias.recibirGolpe(pj.atacar());
                    System.out.println("Has atacado. La vida de Artorias es ahora " + artorias.getVidaActual() + " puntos.");
                }
                break;
            case BEBER:
                pj.beberEstus();
                System.out.println("Has bebido estus. La vida actual del personaje es de " + pj.getVidaActual() + " puntos.");
                break;
            case BEBER_ESTUS_MANA:
                if (pj.getClase().equalsIgnoreCase("mago")) {
                    pj.beberEstusMana();
                    System.out.println("Has bebido estus de maná. El maná actual del personaje es de " + pj.getMana() + " puntos.");
                }
                break;
            case CREAR_LAGRIMA:
                if (pj.getClase().equalsIgnoreCase("mago") && clon == null) {
                    clon = new Personaje(pj.getNombre() + " (Clon)", pj.getClase(), 500, pj.getFuerza(), pj.getResistencia());
                    Arma bastonClon = new Arma("Bastón Arcano", 70);
                    Escudo parmaClon = new Escudo("Parma Arcano", 30);
                    clon.equiparArma(bastonClon);
                    clon.equiparEscudo(parmaClon);
            
                    System.out.println("Has creado una lágrima mimética. El clon absorberá daño hasta que sea derrotado.");
                    System.out.println("El clon está equipado con el arma: " + bastonClon.nombre() + " y el escudo: " + parmaClon.nombre());
                }
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
        return clon;
    }

    /**
     * Metodo para aniadir dificultad al personaje Artorias
     * Aumenta la vida maxima en 150 puntos y su fuerza en 20 puntos
     * @param artorias
     */
    public static void aumentarDificultad(Personaje artorias) {
        System.out.println("¡La dificultad ha aumentado! Artorias ahora tiene más vida y hace más danio.");
        artorias.nuevaVidaMaxima(150);
        artorias.nuevaFuerza(20);
    }

    /**
     * Metodo para intentar huir del combate
     * @return true si huye, false si no
     */
    public static boolean intentarHuir(Personaje pj) {
        Random random = new Random();
        int probabilidad = random.nextInt(100); // Número entre 0 y 99.
        if (probabilidad < 10) { // 10% de probabilidad de éxito.
            System.out.println("Has logrado huir del combate.");
            return true;
        } else {
            System.out.println("antes: "+pj.getVidaActual());
            pj.setVidaActual(pj.getVidaActual()-100);
            System.out.println("despues: "+pj.getVidaActual());
            System.out.println("No has podido huir.");
            return false;
        }
    }

    public static void main(String[] args) {
        int victorias = 0;
        do {
            Escudo escudoArtorias = new Escudo("Gran Escudo de Artorias", 80);
            Arma armaArtorias = new Arma("Espadón de Artorias", 60);
            Personaje artorias = new Personaje("Artorias", "Boss", 3000, 200, 150, escudoArtorias, armaArtorias);
    
            Personaje personaje = crearPersonaje();
    
            if (personaje.getClase().equals("mago")) {
                Arma baston = new Arma("Bastón Arcano", 70);
                personaje.equiparArma(baston);
                Escudo parma = new Escudo("Parma Arcano", 30);
                personaje.equiparEscudo(parma);
            } else {
                Escudo escudoPersonaje = new Escudo("Escudo emblema hierba", 30);
                personaje.equiparEscudo(escudoPersonaje);
                Arma armaPersonaje = new Arma("Claymore", 40);
                personaje.equiparArma(armaPersonaje);
            }
    
            Personaje clon = null;
    
            boolean enCombate = true;
            do {
                Random random = new Random();
                int turno = random.nextInt(4);
    
                if (turno == 1) {
                    System.out.println("--Turno de Artorias ---");
                    int esquivarAtaque = random.nextInt(4);
                    if (esquivarAtaque != 1) {
                        int danio = artorias.atacar();
                        if (clon != null) {
                            System.out.println("El clon está siendo atacado...");
                            if (danio >= clon.getVidaActual()) {
                                int danioRestante = danio - clon.getVidaActual();
                                clon.recibirGolpe(clon.getVidaActual());
                                System.out.println("El clon ha muerto. danio restante: " + danioRestante);
                                clon = null;
    
                                if (danioRestante > 0) {
                                    personaje.recibirGolpe(danioRestante);
                                    System.out.println("El personaje principal ha recibido el danio restante.");
                                }
                            } else {
                                clon.recibirGolpe(danio);
                                System.out.println("La vida del clon es ahora: " + clon.getVidaActual());
                            }
                        } else {
                            personaje.recibirGolpe(danio);
                            System.out.println("La vida de nuestro personaje después del ataque es de: " + personaje.getVidaActual());
                        }
                    } else {
                        System.out.println("¡Ataque esquivado!");
                    }
                } else {
                    System.out.println("--Turno del personaje ---");
                    // Añadimos un menú para que el jugador pueda intentar huir
                    Scanner sc = new Scanner(System.in);
                    System.out.println("1. Atacar");
                    System.out.println("2. Beber estus");
                    if (personaje.getClase().equalsIgnoreCase("mago") && clon == null) {
                        System.out.println("3. Consumir estus de maná");
                        System.out.println("4. Crear lágrima mimética");
                    }
                    System.out.println("5. Intentar huir");
    
                    int opcion;
                    do {
                        System.out.println("Introduce una opción: ");
                        while (!sc.hasNextInt()) { // Manejo de errores por entrada inválida.
                            System.out.println("Por favor, introduce un número válido.");
                            sc.next();
                        }
                        opcion = sc.nextInt();
                    } while (opcion < 1 || opcion > 5);
    
                    if (opcion == 1) {
                        artorias.recibirGolpe(personaje.atacar());
                        System.out.println("Has atacado. La vida de Artorias es ahora: " + artorias.getVidaActual());
                    } else if (opcion == 2) {
                        personaje.beberEstus();
                        System.out.println("Has bebido estus. La vida actual del personaje es de " + personaje.getVidaActual());
                    } else if (opcion == 3 && personaje.getClase().equalsIgnoreCase("mago")) {
                        personaje.beberEstusMana();
                        System.out.println("Has bebido estus de maná. El maná actual del personaje es de " + personaje.getMana());
                    } else if (opcion == 4 && personaje.getClase().equalsIgnoreCase("mago") && clon == null) {
                        clon = new Personaje(personaje.getNombre() + " (Clon)", personaje.getClase(), 500, personaje.getFuerza(), personaje.getResistencia());
                        accion(personaje, artorias, clon);
                        System.out.println("Has creado una lágrima mimética. El clon absorberá danio hasta que sea derrotado.");
                    } else if (opcion == 5) {
                        if (intentarHuir(personaje)) {
                            enCombate = false; // Escapas del combate
                            break;
                        }
                    } else {
                        System.out.println("Opción inválida.");
                    }
                }
    
                if (artorias.getVidaActual() <= 0) {
                    System.out.println("Artorias es el que ha muerto. ¡Has ganado!");
                    victorias++;
                    enCombate = false;
                }
                if (personaje.getVidaActual() <= 0) {
                    System.out.println("Nuestro personaje es el que ha muerto. ¡Has perdido!");
                    enCombate = false;
                }
    
            } while (enCombate);
    
            System.out.println("Fin de la partida");
            if (victorias >= 2) 
                personaje.subirNivel(); // Subir de nivel
            
            if (victorias % 3 == 0) 
                aumentarDificultad(artorias);
    
        } while (jugarDeNuevo());
    }    
}