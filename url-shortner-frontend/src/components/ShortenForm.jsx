import { useState } from "react";
import { shortenUrl } from "../api/urlApi";

function ShortenForm({ onShorten }) {

    const [longUrl, setLongUrl] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        setError("");

        if (!longUrl.trim()) {
            setError("Please enter a URL.");
            return;
        }

        try {
            setLoading(true);

            const data = await shortenUrl(longUrl.trim());

            onShorten(data);
            setLongUrl("");

        } catch (error) {
            console.error(error);
            setError("Unable to shorten this URL. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit}>

            <label className="mb-2 block text-sm font-medium text-slate-700">
                Enter URL
            </label>

            <div className="flex flex-col gap-3 sm:flex-row">

                <input
                    type="url"
                    value={longUrl}
                    onChange={(e) => setLongUrl(e.target.value)}
                    placeholder="https://example.com/your-long-url"
                    disabled={loading}
                    className="min-w-0 flex-1 rounded-lg border border-slate-300 bg-white px-4 py-3 text-sm text-slate-900 outline-none placeholder:text-slate-400 focus:border-blue-500 focus:ring-2 focus:ring-blue-100 disabled:bg-slate-50"
                />

                <button
                    type="submit"
                    disabled={loading}
                    className="rounded-lg bg-blue-600 px-6 py-3 text-sm font-medium text-white transition hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-50"
                >
                    {loading ? "Shortening..." : "Shorten"}
                </button>

            </div>

            {error && (
                <p className="mt-2 text-sm text-red-600">
                    {error}
                </p>
            )}

        </form>
    );
}

export default ShortenForm;