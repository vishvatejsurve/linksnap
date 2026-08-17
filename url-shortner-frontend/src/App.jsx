import { useState } from "react";
import ShortenForm from "./components/ShortenForm";
import ShortLinkResult from "./components/ShortLinkResult";
import Analytics from "./components/Analytics";

function App() {
    const [shortUrl, setShortUrl] = useState(null);

    return (
        <main className="min-h-screen bg-slate-50 px-4 py-12 sm:py-16">

            <div className="mx-auto w-full max-w-3xl">

              
                <header className="mb-8">
                    <h1 className="text-3xl font-bold tracking-tight text-slate-900">
                        URL Shortener
                    </h1>

                    <p className="mt-2 text-sm text-slate-500">
                        Create short links and track their clicks.
                    </p>
                </header>

                
                <section className="rounded-xl border border-slate-200 bg-white p-5 shadow-sm sm:p-6">

                    <ShortenForm onShorten={setShortUrl} />

                    <ShortLinkResult result={shortUrl} />

                </section>

            
                <Analytics />

              
                <footer className="mt-10 text-center text-xs text-slate-400">
                    URL Shortener
                </footer>

            </div>

        </main>
    );
}

export default App;