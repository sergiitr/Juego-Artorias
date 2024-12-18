
import java.util.Random;
public class Personaje {
    public Personaje() {
    }

    public Personaje(String nombre, String clase, int vidaMaxima, int fuerza, int resistencia) {
        this.nombre = nombre;
        this.clase = clase;
        this.vidaMaxima = vidaMaxima;
        this.fuerza = fuerza;
        this.resistencia = resistencia;
        this.vidaActual = vidaMaxima;
        this.mana = 0;
    }

    public Personaje(String nombre, String clase, int vidaMaxima, int fuerza, int resistencia, int mana) {
        this(nombre, clase, vidaMaxima, fuerza, resistencia);
        this.mana = mana;
    }

    public Personaje(String nombre, String clase, int vidaMaxima, int fuerza, int resistencia, Escudo escudo, Arma arma) {
        this(nombre, clase, vidaMaxima, fuerza, resistencia);
        this.escudo = escudo;
        this.arma = arma;
    }
    public String getNombre() {
        return nombre;
    }
    
    public int getFuerza() {
        return fuerza;
    }
    
    public int getResistencia() {
        return resistencia;
    }
    
    public String getClase() {
        return clase;
    }

    public int getMana() {
        return this.mana;
    }

    public void gastarMana(int cantidad) {
        this.mana -= cantidad;
    }

    public void beberEstusMana() {
        if (this.estusMana > 0) {
            this.mana = 500; // Recupera todo el maná
            this.estusMana--;
        } else {
            System.out.println("No te quedan estus de maná");
        }
    }

    public int atacarConBaston() {
        Random random = new Random();
        int ataque = (this.fuerza + this.arma.danio());
        int ataqueCritico = random.nextInt(10);
        if (ataqueCritico == 1) {
            ataque *= 2;
            System.out.println("Hiciste un ataque crítico con el bastón");
        }
        return ataque;
    }
    public void setVidaActual(int aux) {
        this.vidaActual = aux;
    }
    public int getVidaActual() {
        return this.vidaActual;
    }
    public void nuevaVidaMaxima(int nuevavidaMaxima) {
        this.vidaMaxima += nuevavidaMaxima;
    }
    public void nuevaFuerza(int nuevaFuerza) {
        this.fuerza += nuevaFuerza;
    }
    /**
     * Se calcula la defensa del Personaje
     * @return defensaPesonaje
     */
    private int defensa() {
        int defensaPersonaje=this.escudo.defensa()+2*this.resistencia;
        return defensaPersonaje;
    }

    /**
     * Funcion morir
     * Sale un mensaje por pantalla de que ha
     */
    private void morir() {
        this.vidaActual=0;
        System.out.println("HAS MUERTO");
    }

    /**
     * Hacemos el metodo recibir golpe
     * Si el golpe es mayor que la suma de la vida actual y del escudo, el personaje se muere
     * Si el golpe es mayor que la defensa del escudo, al guerrero le baja la vida
     * En caso contrario, la vidaActual se mantiene 
     * @param golpe
     */
    public void recibirGolpe(int golpe) {
        int defensaEscudo = (this.escudo != null) ? this.escudo.defensa() : 0; // Si el escudo es null, la defensa es 0.
    
        if (golpe >= (this.vidaActual + defensaEscudo)) {
            morir();
        } else if (golpe > defensaEscudo) {
            this.vidaActual = this.vidaActual - (golpe - defensaEscudo);
        } else {
            System.out.println("El golpe fue bloqueado completamente.");
        }
    }

    /**
     * Hacemos el metodo atacar.
     * El ataque es la fuera nuestra mas la del arma
     * @return ataque
     */
    public int atacar() {
        Random random = new Random();
        int ataqueCritico = random.nextInt(10);
        int ataque = (this.fuerza+arma.danio());

        if (ataqueCritico==1) {
            System.out.println("Hiciste un ataque critico");
            ataque*=2;
        }
        return ataque;
    }

    /**
     * Metodo beberEstus
     * Sila resistencia*3 es mayor que la vidaMaxima, se devuelve la totalidad de la vida, en caso contrario, solamente la resistencia*3
     * Cada vez que bebamos decrementamos la pocima en 1. Si llegamos a 0 no podemos volver a beber
     */
    public void beberEstus() {
        if(this.estus>0) {
            if ( this.resistencia*3 >= this.vidaMaxima  )
                this.vidaActual = this.vidaMaxima;
            else
                this.vidaActual += this.resistencia*3;
            
            this.estus -= 1;
        } else
            System.out.println("No te quedan estus");
    }

    /**
     * Equipamos arma
     * @param arma
     */
    public void equiparArma(Arma arma) {
        if (this.arma != null) 
            this.arma=null;
        
        this.arma=arma;
        System.out.println("Se acaba de equipar con una arma "+arma.nombre());
    }

    /**
     * Equipamos escudo
     * @param escudo
     */
    public void equiparEscudo(Escudo escudo) {
        if(this.escudo != null) 
            this.escudo = null;
        
        this.escudo = escudo;
        System.out.println("Se acaba de equipar con un escudo "+escudo.nombre());
    }

    public void esquivarAtaque() {
        System.out.println("Ataque esquivado");
    }

    /**
     * Metodo para subir de nivel a nuestro personaje
     * Con cada subida de nivel aumenta los atributos
     */
    public void subirNivel() {
        // Incrementar nivel, fuerza y vida máxima
        this.nivel++;
        this.fuerza += 10;
        this.vidaMaxima += 50;
        this.vidaActual = this.vidaMaxima;
        System.out.println("¡Nivel subido! Ahora eres nivel " + this.nivel);
        System.out.println("Nueva fuerza: " + this.fuerza);
        System.out.println("Nueva vida máxima: " + this.vidaMaxima);
    }
    
    private int nivel = 1; // Nivel inicial

    private String nombre;
    private String clase;
    private int vidaMaxima;
    private int vidaActual;
    private int fuerza;
    private int resistencia;
    private int estus=10;
    private Escudo escudo;
    private Arma arma;
    private int estusMana = 3;
    private int mana;
}
