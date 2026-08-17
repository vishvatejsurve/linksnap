const BASE_URL ="http://localhost:8080";

export async function shortenUrl(longUrl) 
{
    const response = await fetch(`${BASE_URL}/api/shorten`,{
       method:"POST",
       headers: {"Content-Type":"application/json"},
       body: JSON.stringify({longUrl : longUrl,}), 
    });

    if(!response.ok)
    {
        throw new Error("Failed to shorten URL");
        
    }
    return response.json();
}

export async function getAnalytics(code) {
    const response= await fetch(
        `${BASE_URL}/api/analytics/${code}`
    );

    if(!response.ok)
    {
        throw new Error("Failed to fetch analytics");
    }
    
    return response.json();
}