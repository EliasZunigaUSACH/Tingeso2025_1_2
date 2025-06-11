import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const years = Array.from({ length: 10 }, (_, i) => 2020 + i);
const months = [
  { value: 1, label: "Enero" },
  { value: 2, label: "Febrero" },
  { value: 3, label: "Marzo" },
  { value: 4, label: "Abril" },
  { value: 5, label: "Mayo" },
  { value: 6, label: "Junio" },
  { value: 7, label: "Julio" },
  { value: 8, label: "Agosto" },
  { value: 9, label: "Septiembre" },
  { value: 10, label: "Octubre" },
  { value: 11, label: "Noviembre" },
  { value: 12, label: "Diciembre" },
];

export default function AddReport() {
  const [startYear, setStartYear] = useState(years[0]);
  const [startMonth, setStartMonth] = useState(months[0].value);
  const [endYear, setEndYear] = useState(years[0]);
  const [endMonth, setEndMonth] = useState(months[0].value);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    // Aquí deberías llamar a tu API para generar el reporte
    // Simulación de éxito:
    // await fetch(...);
    navigate("/report/list");
  };

  return (
    <div>
      <h2>Generar Reporte</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Año de inicio:</label>
          <select value={startYear} onChange={e => setStartYear(Number(e.target.value))}>
            {years.map(y => <option key={y} value={y}>{y}</option>)}
          </select>
          <label>Mes de inicio:</label>
          <select value={startMonth} onChange={e => setStartMonth(Number(e.target.value))}>
            {months.map(m => <option key={m.value} value={m.value}>{m.label}</option>)}
          </select>
        </div>
        <div>
          <label>Año de fin:</label>
          <select value={endYear} onChange={e => setEndYear(Number(e.target.value))}>
            {years.map(y => <option key={y} value={y}>{y}</option>)}
          </select>
          <label>Mes de fin:</label>
          <select value={endMonth} onChange={e => setEndMonth(Number(e.target.value))}>
            {months.map(m => <option key={m.value} value={m.value}>{m.label}</option>)}
          </select>
        </div>
        <button type="submit">Generar Reporte</button>
      </form>
    </div>
  );
}