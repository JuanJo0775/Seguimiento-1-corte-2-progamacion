package org.example.domain;

public class CitaMedica {
    private Paciente paciente;
    private String motivo;
    private String fecha;
    private static int numeroCita = 0;
    private String hora;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public static int getNumeroCita() {
        return numeroCita;
    }

    public static void setNumeroCita(int numeroCita) {
        CitaMedica.numeroCita = numeroCita;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "citaMedica{" +
                "paciente=" + paciente +
                ", motivo='" + motivo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
