import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Table, TableBody, TableCell, TableContainer,
  TableHead, TableRow, Paper, Button
} from "@mui/material";
import PictureAsPdfIcon from "@mui/icons-material/PictureAsPdf";

const ReceiptList = () => {
  const [receipts, setReceipts] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get("http://localhost:8080/api/v1/receipts")
      .then((res) => {
        setReceipts(res.data);
        setLoading(false);
      })
      .catch(() => setLoading(false));
  }, []);

  if (loading) return <div>Cargando recibos...</div>;

  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Nombre del Cliente
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Fecha de Creación
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              ID Reserva
            </TableCell>
            <TableCell align="center" sx={{ fontWeight: "bold" }}>
              PDF
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {receipts.map((receipt) => (
            <TableRow key={receipt.id}>
              <TableCell align="left">{receipt.clienteNombre}</TableCell>
              <TableCell align="right">
                {new Date(receipt.fechaCreacion).toLocaleDateString()}
              </TableCell>
              <TableCell align="right">{receipt.reservaId}</TableCell>
              <TableCell align="center">
                <Button
                  variant="contained"
                  color="primary"
                  size="small"
                  href={receipt.pdfUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  startIcon={<PictureAsPdfIcon />}
                >
                  Ver PDF
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ReceiptList;