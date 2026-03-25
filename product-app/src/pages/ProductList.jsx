import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getProducts, deleteProduct } from '../api/productApi';

function ProductList() {
  const [products, setProducts] = useState([]);
  const navigate = useNavigate();

  const fetchProducts = () => {
    getProducts().then(res => setProducts(res.data));
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  // 삭제 처리
  const handleDelete = (id) => {
    if (window.confirm('정말 삭제하시겠습니까?')) {
      deleteProduct(id).then(() => fetchProducts());
    }
  };

  return (
    <div className="space-y-4">
      <div className="flex items-end justify-between">
        <h1 className="text-2xl font-bold text-slate-800">상품 목록</h1>
        <button
          onClick={() => navigate('/product/new')}
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
        >
          등록
        </button>
      </div>

      <div className="overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm">
        <div className="grid grid-cols-[1.5fr_1fr_1fr_auto] gap-2 border-b border-slate-200 bg-slate-100 px-4 py-3 text-sm font-semibold text-slate-700">
          <span>상품명</span>
          <span>가격</span>
          <span>재고</span>
          <span className="text-right">관리</span>
        </div>

        {products.length === 0 && (
          <div className="px-4 py-8 text-center text-slate-500">등록된 상품이 없습니다.</div>
        )}

        {products.map(product => (
          <div key={product.id} className="grid grid-cols-[1.5fr_1fr_1fr_auto] gap-2 border-b border-slate-100 px-4 py-3 text-sm last:border-b-0">
            <span className="font-medium text-slate-800">{product.name}</span>
            <span className="text-slate-600">{Number(product.price).toLocaleString()}원</span>
            <span className="text-slate-600">{product.stock}</span>
            <div className="flex justify-end gap-2">
              <button
                onClick={() => navigate(`/product/${product.id}`)}
                className="rounded border border-slate-300 px-3 py-1.5 text-slate-700 hover:bg-slate-50"
              >
                상세
              </button>
              <button
                onClick={() => handleDelete(product.id)}
                className="rounded border border-rose-300 px-3 py-1.5 text-rose-600 hover:bg-rose-50"
              >
                삭제
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ProductList;