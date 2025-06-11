import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import rackService from "../services/rack.service";
import reservaService from "../services/reservation.service";
import { format, addDays, startOfWeek, subWeeks, addWeeks } from "date-fns";
import { es } from "date-fns/locale";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import EditIcon from "@mui/icons-material/Edit";
import RequestQuoteIcon from "@mui/icons-material/RequestQuote";
import GoEyeIcon from "@mui/icons-material/Visibility";
import Typography from "@mui/material/Typography";

const daysOfWeek = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"];

const getTimeBlocks = () => [
  "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
  "18:00", "19:00", "20:00", "21:00", "22:00",
];

const RackList = () => {
  const [racks, setRacks] = useState([]); // Todos los racks de la semana
  const [reservas, setReservas] = useState([]); // Reservas de la semana
  const [currentWeek, setCurrentWeek] = useState(startOfWeek(new Date(), { weekStartsOn: 1 }));
  const todayWeek = startOfWeek(new Date(), { weekStartsOn: 1 });
  const [selectedRack, setSelectedRack] = useState(null);
  const [selectedReserva, setSelectedReserva] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const navigate = useNavigate();

  const handleOpenModal = (rack, reserva) => {
    setSelectedRack(rack);
    setSelectedReserva(reserva || null);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setSelectedRack(null);
    setSelectedReserva(null);
    setIsModalOpen(false);
  };

  // Obtiene las fechas de la semana actual
  const getDatesForWeek = () => {
    const start = startOfWeek(currentWeek, { weekStartsOn: 1 });
    return daysOfWeek.map((_, index) => {
      const date = addDays(start, index);
      return format(new Date(date.setHours(0, 0, 0, 0)), "yyyy-MM-dd", { locale: es });
    });
  };

  // Carga todos los racks y reservas de la semana
  const loadWeekData = async () => {
    const datesForWeek = getDatesForWeek();
    try {
      // Trae todos los racks de la semana
      const racksResponse = await rackService.getAll();
      const allRacks = racksResponse.data.map((rack) => ({
        ...rack,
        date: format(new Date(rack.date), "yyyy-MM-dd"),
        startTime: rack.startTime.slice(0, 5),
      }));

      // Trae todas las reservas de la semana
      const reservasResponse = await reservaService.getAll(); // Ajusta según tu API
      const allReservas = reservasResponse.data.map((reserva) => ({
        ...reserva,
        date: format(new Date(reserva.date), "yyyy-MM-dd"),
        startTime: reserva.startTime.slice(0, 5),
      }));

      setReservas(allReservas);

      // Genera todos los bloques de la semana, aunque no existan racks en la BD
      const weekRacks = [];
      getTimeBlocks().forEach((time) => {
        daysOfWeek.forEach((day, idx) => {
          const date = datesForWeek[idx];
          // Busca si existe un rack en la BD para ese bloque
          const rack = allRacks.find((r) => r.date === date && r.startTime === time);
          weekRacks.push({
            day,
            date,
            time,
            rack: rack || null,
          });
        });
      });
      setRacks(weekRacks);
    } catch (error) {
      setRacks([]);
      setReservas([]);
      console.log("Error al obtener racks o reservas.", error);
    }
  };

  useEffect(() => {
    loadWeekData();
    // eslint-disable-next-line
  }, [currentWeek]);

  const isUnavailable = (day, time) => {
    const weekdays = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes"];
    return weekdays.includes(day) && ["10:00", "11:00", "12:00", "13:00"].includes(time);
  };

  // Busca el rack y la reserva para un bloque
  const getRackAndReservaForBlock = (day, time) => {
    const date = getDatesForWeek()[daysOfWeek.indexOf(day)];
    const rackBlock = racks.find((r) => r.day === day && r.time === time && r.date === date);
    const reserva = reservas.find(
      (res) => res.date === date && res.startTime === time
    );
    return { rack: rackBlock ? rackBlock.rack : null, reserva };
  };

  const datesForWeek = getDatesForWeek();

  const handlePreviousWeek = () => {
    setCurrentWeek((prevWeek) => subWeeks(prevWeek, 1));
  };

  const handleNextWeek = () => {
    setCurrentWeek((prevWeek) => addWeeks(prevWeek, 1));
  };

  const handleCreateReserva = async (day, time) => {
    const date = getDatesForWeek()[daysOfWeek.indexOf(day)];
    // Busca si existe rack
    let rack = racks.find((r) => r.day === day && r.time === time && r.rack);
    let rackId = rack?.rack?.id;

    try {
      if (!rackId) {
        // Si no existe rack, créalo
        const newRack = await rackService.create({
          date,
          startTime: time,
          // agrega otros campos necesarios según tu modelo
        });
        rackId = newRack.data.id;
        // Recarga los datos para reflejar el nuevo rack
        await loadWeekData();
      }
      // Redirige a crear reserva para ese rack
      navigate(`/reservation/add?rackId=${rackId}`);
    } catch (error) {
      alert("Error al crear rack o reserva");
    }
  };

  return (
    <div>
      <div style={{ display: "flex", justifyContent: "space-between", marginBottom: "1rem" }}>
        {currentWeek > todayWeek ? (
          <Button
            variant="contained"
            style={{ backgroundColor: "black", color: "white" }}
            onClick={handlePreviousWeek}
          >
            Semana Anterior
          </Button>
        ) : (
          <div /> // Espacio vacío para mantener alineación
        )}
        <h2>Semana del {format(currentWeek, "dd 'de' MMMM 'de' yyyy", { locale: es })}</h2>
        <Button
          variant="contained"
          style={{ backgroundColor: "black", color: "white" }}
          onClick={handleNextWeek}
        >
          Semana Siguiente
        </Button>
      </div>
      <TableContainer component={Paper}>
        <br />
        <Table sx={{ minWidth: 650 }} size="small" aria-label="rack semanal">
          <TableHead>
            <TableRow>
              {daysOfWeek.map((day, index) => (
                <TableCell key={day} align="center" sx={{ fontWeight: "bold" }}>
                  {day} <br /> {datesForWeek[index]}
                </TableCell>
              ))}
              <TableCell align="center" sx={{ fontWeight: "bold" }}>
                Hora
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {getTimeBlocks().map((time) => (
              <TableRow key={time}>
                {daysOfWeek.map((day, index) => {
                  if (isUnavailable(day, time)) {
                    return (
                      <TableCell key={index} align="center" sx={{ color: "gray" }}>
                        No aplica
                      </TableCell>
                    );
                  }
                  const { rack, reserva } = getRackAndReservaForBlock(day, time);
                  return (
                    <TableCell
                      key={index}
                      align="center"
                      sx={{
                        cursor: rack ? "default" : "pointer",
                        backgroundColor: rack ? "#ffecb3" : "#e0f7fa",
                        border: rack ? "1px solid #ffa000" : "none",
                      }}
                    >
                      {rack ? (
                        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", gap: "0.3rem" }}>
                          <div style={{ display: "flex", gap: "0.3rem" }}>
                            <Button
                              variant="contained"
                              color="info"
                              size="small"
                              onClick={() => navigate(`/rack/edit/${rack.id}`)}
                              style={{ minWidth: "40px", padding: "0.2rem" }}
                            >
                              <EditIcon />
                            </Button>
                            <Button
                              variant="contained"
                              color="primary"
                              size="small"
                              onClick={() => navigate(`/rack/view/${rack.id}`)}
                              style={{ minWidth: "40px", padding: "0.2rem" }}
                            >
                              <RequestQuoteIcon />
                            </Button>
                            <Button
                              variant="contained"
                              color="secondary"
                              size="small"
                              onClick={() => handleOpenModal(rack, reserva)}
                              style={{ minWidth: "40px", padding: "0.2rem" }}
                            >
                              <GoEyeIcon />
                            </Button>
                          </div>
                          {reserva ? (
                            <Typography variant="caption" color="success.main">
                              Reservado por: {reserva.usuario || reserva.nombre || "Usuario"}
                            </Typography>
                          ) : (
                            <Typography variant="caption" color="text.secondary">
                              Sin reserva
                            </Typography>
                          )}
                        </div>
                      ) : (
                        <Button
                          variant="outlined"
                          color="success"
                          size="small"
                          onClick={() => handleCreateReserva(day, time)}
                        >
                          Crear Reserva
                        </Button>
                      )}
                    </TableCell>
                  );
                })}
                <TableCell align="center" sx={{ fontWeight: "bold" }}>
                  {time}
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default RackList;
