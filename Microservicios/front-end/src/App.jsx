import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import ClientList from './components/ClientList';
import AddEditClient from './components/AddEditClient';
import RackList from './components/RackList';
import AddEditReservation from './components/AddEditReservation';
import NotFound from './components/NotFound';
import ReceiptList from './components/ReceiptList';
import ReportList from './components/ReportList';
import AddReport from './components/AddReport';

function App() {
  return (
      <Router>
        <div className='gradient-background'>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="/client/list" element={<ClientList/>} />
              <Route path="/client/add" element={<AddEditClient/>} />
              <Route path="/client/edit/:id" element={<AddEditClient/>} />
              <Route path="/rack" element={<RackList/>}/>
              <Route path="/reservation/add" element={<AddEditReservation/>} />
              <Route path="/reservation/edit/:id" element={<AddEditReservation/>} />
              <Route path="/receipt/list" element={<ReceiptList/>}/>
              <Route path="/report/list" element={<ReportList/>}/>
              <Route path="/report/add" element={<AddReport/>} />
              <Route path="*" element={<NotFound/>} />
            </Routes>
          </div>
        </div>
      </Router>
  );
}

export default App