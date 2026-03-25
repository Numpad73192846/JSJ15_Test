import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Navbar from './components/Navbar'
import ProductList from './pages/ProductList'
import ProductForm from './pages/ProductForm'
import ProductDetail from './pages/ProductDetail'

function App() {

  return (
    <BrowserRouter>
      <div className="min-h-screen bg-slate-50">
        <Navbar />
        <main className="mx-auto max-w-4xl px-4 py-8">
          <Routes>
            <Route path='/' element={<ProductList />} />
            <Route path='/product/new' element={<ProductForm />} />
            <Route path='/product/:id' element={<ProductDetail />} />
            <Route path='/product/:id/edit' element={<ProductForm />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  );

}

export default App
