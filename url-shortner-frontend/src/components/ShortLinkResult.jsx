import { useState } from "react";

function ShortLinkResult({ result }) {

    const [copied, setCopied] = useState(false);

    if (!result) {
        return null;
    }

    const copyUrl = async () => {

        try {
            await navigator.clipboard.writeText(result.shortUrl);

            setCopied(true);

            setTimeout(() => {
                setCopied(false);
            }, 2000);

        } catch (error) {
            console.error("Copy failed:", error);
        }
    };

    return (
        <div className="mt-6 rounded-lg border border-slate-200 bg-slate-50 p-5">

            <p className="mb-2 text-sm font-medium text-slate-700">
                Short URL
            </p>

            <div className="flex flex-col gap-2 sm:flex-row">

                <div className="min-w-0 flex-1 rounded-lg border border-slate-200 bg-white px-4 py-3">

                    <p className="truncate font-mono text-sm text-blue-600">
                        {result.shortUrl}
                    </p>

                </div>

                <button
                    type="button"
                    onClick={copyUrl}
                    className={`rounded-lg px-5 py-3 text-sm font-medium transition ${
                        copied
                            ? "bg-green-600 text-white"
                            : "border border-slate-300 bg-white text-slate-700 hover:bg-slate-50"
                    }`}
                >
                    {copied ? "Copied" : "Copy"}
                </button>

            </div>

            <div className="mt-4 flex flex-col gap-2 border-t border-slate-200 pt-4 text-sm sm:flex-row sm:items-center sm:justify-between">

                <p className="text-slate-500">
                    Short code:
                    <span className="ml-2 font-mono text-slate-700">
                        {result.shortCode}
                    </span>
                </p>

                <a
                    href={result.shortUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="font-medium text-blue-600 hover:text-blue-700"
                >
                    Open link →
                </a>

            </div>

        </div>
    );
}

export default ShortLinkResult;