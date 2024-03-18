use std::error::Error;

use clap::Parser;

#[derive(Parser, Debug)]
struct Args {
    path: String
}

fn main() -> Result<(), Box<dyn Error>> {
    let args = Args::parse();

    Ok(())
}

fn run(sourse: &str) {
    let scanner = Scanner::new(sourse);
    let tokens = scanner.scan_tokens();

    for token in tokens {
        println!("{}", token);
    }
}
