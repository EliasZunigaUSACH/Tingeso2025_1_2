import React from "react";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import reportService from "../services/report.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import VisibilityIcon from "@mui/icons-material/Visibility";
import AddIcon from "@mui/icons-material/Add";

function formatYearMonth(dateStr) {
  const date = new Date(dateStr);
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}`;
}

const ReportList = () => {
    const [reports, setReports] = useState([]);

    const init = () => {
        reportService
            .getAll()
            .then((response) => {
                console.log("Mostrando listado de todos los reportes.", response.data);
                setReports(response.data);
            })
            .catch((error) => {
                console.log(
                    "Se ha producido un error al intentar mostrar listado de todos los reportes.",
                    error
                );
            });
    };

    useEffect(() => {
        init();
    }, []);

    const handleViewReport = (id) => {
        alert(`Ver reporte con ID: ${id}`);
    };

    return (
      
        <TableContainer component={Paper}>
          <br/>
        <Link
          to ="/report/add"
          style={{ textDecoration: "none" , marginBottom: "1rem" }}>
          <Button
            variant="contained"
            color="success"
            startIcon={<AddIcon />}
            style={{ marginBottom: "1rem" }}
          >
            Crear Reporte
          </Button>
        </Link>
        <br/>
          <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
            <TableHead>
              <TableRow>
                <TableCell align="left" sx={{ fontWeight: "bold" }}>
                  ID
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Fecha Inicio (Año-Mes)
                </TableCell>
                <TableCell align="right" sx={{ fontWeight: "bold" }}>
                  Fecha Fin (Año-Mes)
                </TableCell>
                <TableCell align="center" sx={{ fontWeight: "bold" }}>
                  Acciones
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {reports.map((report) => (
                <TableRow key={report.id}>
                  <TableCell align="left">{report.id}</TableCell>
                  <TableCell align="right">{formatYearMonth(report.startDate)}</TableCell>
                  <TableCell align="right">{formatYearMonth(report.endDate)}</TableCell>
                  <TableCell align="center">
                    <Button
                      variant="contained"
                      color="primary"
                      size="small"
                      onClick={() => handleViewReport(report.id)}
                      startIcon={<VisibilityIcon />}
                    >
                      Ver Reporte
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
    );
};

export default ReportList;