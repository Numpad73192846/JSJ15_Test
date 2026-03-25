import { Link } from "react-router-dom";

function Navbar() {
    return (
        <nav className="border-b border-slate-200 bg-white/90 backdrop-blur">
            <div className="mx-auto flex max-w-4xl items-center justify-between px-4 py-4">
                <Link to={"/"} className="text-xl font-bold text-slate-800">상품관리</Link>
                <div className="flex gap-2">
                    <Link
                        to={"/"}
                        className="rounded px-3 py-2 text-sm font-medium text-slate-700 hover:bg-slate-100"
                    >
                        상품 목록
                    </Link>
                    <Link
                        to={"/product/new"}
                        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
                    >
                        상품 등록
                    </Link>
                </div>
            </div>
        </nav>
    );
}

export default Navbar;