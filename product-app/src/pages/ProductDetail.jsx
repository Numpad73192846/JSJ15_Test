import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getProduct } from '../api/productApi';

function ProductDetail() {
	const { id } = useParams();
	const navigate = useNavigate();
	const [product, setProduct] = useState(null);

	useEffect(() => {
		getProduct(id).then((res) => setProduct(res.data));
	}, [id]);

	if (!product) {
		return <div className="py-10 text-center text-slate-500">상품 정보를 불러오는 중...</div>;
	}

	return (
		<div className="mx-auto max-w-xl space-y-6">
			<h1 className="text-2xl font-bold text-slate-800">상품 상세</h1>

			<div className="rounded-xl border border-slate-200 bg-white p-6 shadow-sm">
				<dl className="space-y-3 text-sm">
					<div className="grid grid-cols-[110px_1fr]">
						<dt className="font-semibold text-slate-600">상품명</dt>
						<dd className="text-slate-800">{product.name}</dd>
					</div>
					<div className="grid grid-cols-[110px_1fr]">
						<dt className="font-semibold text-slate-600">가격</dt>
						<dd className="text-slate-800">{Number(product.price).toLocaleString()}원</dd>
					</div>
					<div className="grid grid-cols-[110px_1fr]">
						<dt className="font-semibold text-slate-600">재고</dt>
						<dd className="text-slate-800">{product.stock}</dd>
					</div>
				</dl>
			</div>

			<div className="flex gap-2">
				<button
					onClick={() => navigate(`/product/${id}/edit`)}
					className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
				>
					수정
				</button>
				<button
					onClick={() => navigate('/')}
					className="rounded border border-slate-300 px-4 py-2 text-slate-700 hover:bg-slate-50"
				>
					목록
				</button>
			</div>
		</div>
	);
}

export default ProductDetail;
