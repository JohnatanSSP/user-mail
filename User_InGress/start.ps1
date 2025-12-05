# start.ps1 - loads User_InGress/.env into the current process and starts the service
$envFile = Join-Path $PSScriptRoot '.env'
if (Test-Path $envFile) {
  Get-Content $envFile | ForEach-Object {
    if ($_ -and (-not ($_ -match '^[\s#]'))) {
      $parts = $_ -split '=',2
      if ($parts.Length -eq 2) {
        $name = $parts[0].Trim()
        $value = $parts[1].Trim()
        Write-Host "Setting $name"
        [System.Environment]::SetEnvironmentVariable($name, $value, 'Process')
      }
    }
  }
} else {
  Write-Host "No .env file found in $PSScriptRoot. Proceeding with environment vars already set."
}

# run the application
cd $PSScriptRoot
.\mvnw.cmd -DskipTests spring-boot:run

