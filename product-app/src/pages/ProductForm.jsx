import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getProduct, createProduct, updateProduct } from '../api/productApi';

function ProductForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEdit = !!id;

  const [form, setForm] = useState({ name: '', price: '', stock: '' });

  useEffect(() => {
    if (isEdit) {
      getProduct(id).then(res => setForm(res.data));
    }
  }, [id, isEdit]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!form.name.trim()) {
        alert('상품명을 입력하세요.');
        return;
    }
    
    if (form.price === '' || Number(form.price) < 0) {
        alert('가격은 0 이상의 숫자를 입력하세요.');
        return;
    }

    if (form.stock === '' || Number(form.stock) < 0) {
        alert('재고는 0 이상의 숫자를 입력하세요.');
        return;
    }

    if (isEdit) {
        updateProduct(id, form).then(() => navigate('/'));
    } else {
        createProduct(form).then(() => navigate('/'));
    }

  };

  return (
    <div className="mx-auto max-w-xl">
      <h1 className="mb-6 text-2xl font-bold text-slate-800">{isEdit ? '상품 수정' : '상품 등록'}</h1>

      <form onSubmit={handleSubmit} className="space-y-4 rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
        <div className="space-y-1">
          <label className="text-sm font-medium text-slate-700">상품명</label>
          <input
            name="name"
            value={form.name}
            onChange={handleChange}
            placeholder="상품명"
            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        <div className="space-y-1">
          <label className="text-sm font-medium text-slate-700">가격</label>
          <input
            type="number"
            min="0"
            name="price"
            value={form.price}
            onChange={handleChange}
            placeholder="가격"
            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        <div className="space-y-1">
          <label className="text-sm font-medium text-slate-700">재고</label>
          <input
            type="number"
            min="0"
            name="stock"
            value={form.stock}
            onChange={handleChange}
            placeholder="재고"
            className="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        <div className="flex gap-2 pt-2">
          <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700">
            {isEdit ? '수정' : '등록'}
          </button>
          <button
            type="button"
            onClick={() => navigate(-1)}
            className="rounded border border-slate-300 px-4 py-2 text-slate-700 hover:bg-slate-50"
          >
            취소
          </button>
        </div>
      </form>
    </div>
  );
}

export default ProductForm;