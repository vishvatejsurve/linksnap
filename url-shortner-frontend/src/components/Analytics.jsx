import { useState } from "react";
import { getAnalytics } from "../api/urlApi";

function Analytics() {

    const [shortCode, setShortCode] = useState("");
    const [analytics, setAnalytics] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {

        e.preventDefault();

        setError("");
        setAnalytics(null);

        if (!shortCode.trim()) {
            setError("Please enter a short code.");
            return;
        }

        try {

            setLoading(true);

            const data = await getAnalytics(shortCode.trim());

            setAnalytics(data);

        } catch (error) {

            console.error(error);
            setError("Short code not found.");

        } finally {

            setLoading(false);

        }
    };

    return (
        <section className="mt-10">

            <div className="mb-4">

                <h2 className="text-xl font-semibold text-slate-900">
                    Analytics
                </h2>

                <p className="mt-1 text-sm text-slate-500">
                    Check the statistics of a shortened URL.
                </p>

            </div>

            <div className="rounded-xl border border-slate-200 bg-white p-5 shadow-sm sm:p-6">

             
                <form
                    onSubmit={handleSubmit}
                    className="flex flex-col gap-3 sm:flex-row"
                >

                    <input
                        type="text"
                        value={shortCode}
                        onChange={(e) => setShortCode(e.target.value)}
                        placeholder="Enter short code"
                        disabled={loading}
                        className="min-w-0 flex-1 rounded-lg border border-slate-300 bg-white px-4 py-3 text-sm text-slate-900 outline-none placeholder:text-slate-400 focus:border-blue-500 focus:ring-2 focus:ring-blue-100 disabled:bg-slate-50"
                    />

                    <button
                        type="submit"
                        disabled={loading}
                        className="rounded-lg bg-slate-800 px-6 py-3 text-sm font-medium text-white transition hover:bg-slate-900 disabled:cursor-not-allowed disabled:opacity-50"
                    >
                        {loading ? "Loading..." : "View"}
                    </button>

                </form>

               
                {error && (
                    <p className="mt-3 text-sm text-red-600">
                        {error}
                    </p>
                )}

               
                {analytics && (

                    <div className="mt-6">

                        <div className="grid gap-4 sm:grid-cols-2">

                          
                            <div className="rounded-lg border border-slate-200 bg-slate-50 p-5">

                                <p className="text-sm text-slate-500">
                                    Total Clicks
                                </p>

                                <p className="mt-2 text-3xl font-semibold text-slate-900">
                                    {analytics.clickCount}
                                </p>

                            </div>

                          
                            <div className="rounded-lg border border-slate-200 bg-slate-50 p-5">

                                <p className="text-sm text-slate-500">
                                    Short Code
                                </p>

                                <p className="mt-2 font-mono text-xl font-medium text-blue-600">
                                    {analytics.shortCode}
                                </p>

                            </div>

                        </div>

                       
                        <div className="mt-4 rounded-lg border border-slate-200 bg-slate-50 p-5">

                            <p className="text-sm text-slate-500">
                                Original URL
                            </p>

                            <p className="mt-2 break-all text-sm leading-6 text-slate-700">
                                {analytics.longUrl}
                            </p>

                        </div>

                    
                        <p className="mt-4 text-sm text-slate-500">
                            Created:
                            <span className="ml-2 text-slate-700">
                                {new Date(
                                    analytics.createdAt
                                ).toLocaleString()}
                            </span>
                        </p>

                    </div>

                )}

            </div>

        </section>
    );
}

export default Analytics;